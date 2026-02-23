package com.securefromscratch.busybee.controllers;

import com.securefromscratch.busybee.storage.Task;
import com.securefromscratch.busybee.storage.TaskNotFoundException;
import com.securefromscratch.busybee.storage.TasksStorage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.*;
import com.securefromscratch.busybee.safety.ImageName;
import com.securefromscratch.busybee.safety.Name;
import com.securefromscratch.busybee.safety.Description;
import com.securefromscratch.busybee.safety.FutureDate;
import com.securefromscratch.busybee.safety.ValidTime;
import com.securefromscratch.busybee.safety.ResponsibilityOf;
import com.securefromscratch.busybee.auth.UsersStorage;
import com.securefromscratch.busybee.auth.InvalidResponsibilityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "null")
@PreAuthorize("hasRole('ADMIN')")
public class TasksController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

    @Autowired
    private TasksStorage m_tasks;
    
    @Autowired
    private UsersStorage m_usersStorage;

    @GetMapping("/tasks")
    @PreAuthorize("permitAll()")
    @PostFilter("@taskAuthorization.userAllowedToViewTask(filterObject, authentication)")
    public Collection<TaskOut> getTasks(Principal principal) {
        List<Task> allTasks = m_tasks.getAll();
        Transformer<Task, TaskOut> transformer = t-> TaskOut.fromTask((Task)t);
        return CollectionUtils.collect(allTasks, transformer);
    }

    @PostMapping("/done")
    @PreAuthorize("@taskAuthorization.canCloseTask(#request.taskid, authentication)")
    public ResponseEntity<MarkDoneResponse> markTaskDone(@RequestBody MarkDoneRequest request, Principal principal) throws IOException, TaskNotFoundException {
        // Validate UUID is not null
        if (request == null || request.taskid() == null) {
            return ResponseEntity.badRequest().body(new MarkDoneResponse(false));
        }

        // Check if task exists - return 404 if not
        if (!m_tasks.find(request.taskid()).isPresent()) {
            LOGGER.warn("Task not found when marking as done: {}", request.taskid());
            throw new TaskNotFoundException(request.taskid());
        }

        m_tasks.markDone(request.taskid());
        LOGGER.info("Task marked as done: {}", request.taskid());
        return ResponseEntity.ok(new MarkDoneResponse(true));
    }

    @PostMapping("/create")
    @PreAuthorize("@taskAuthorization.isAuthorizedToCreate(authentication)")
    public ResponseEntity<NewTaskResponse> create(@RequestBody NewTaskRequest request, Authentication authentication) throws IOException, InvalidResponsibilityException {
        if (request == null || request.name() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validate responsibilityOf usernames exist and have correct format
        String[] participants = new String[0];
        if (request.responsibilityOf() != null && !request.responsibilityOf().isEmpty()) {
            ResponsibilityOf responsibility = new ResponsibilityOf(request.responsibilityOf(), m_usersStorage);
            participants = responsibility.getResponsibilities().toArray(new String[0]);
        }

        // Get validated date if provided
        java.time.LocalDate parsedDate = null;
        if (request.dueDate() != null) {
            parsedDate = request.dueDate().getDate();
        }

        java.time.LocalTime parsedTime = null;
        if (request.dueTime() != null) {
            parsedTime = request.dueTime().getTime();
            // Check if date+time combination is in the past
            if (parsedDate != null && request.dueTime().isPastWhen(parsedDate)) {
                return ResponseEntity.badRequest().body(new NewTaskResponse("Error: date+time combination cannot be in the past"));
            }
        }

        String username = authentication.getName();
        String taskName = request.name().toString();
        String taskDesc = request.desc().toString();
        
        // Check if a task with the same name already exists
        for (Task existingTask : m_tasks.getAll()) {
            if (existingTask.name().equals(taskName)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new NewTaskResponse("Error: A task with the name '" + taskName + "' already exists"));
            }
        }
        
        UUID id;
        if (parsedDate != null && parsedTime != null) {
            id = m_tasks.add(taskName, taskDesc, parsedDate, parsedTime, username, participants);
        } else if (parsedDate != null) {
            id = m_tasks.add(taskName, taskDesc, parsedDate, username, participants);
        } else {
            id = m_tasks.add(taskName, taskDesc, username, participants);
        }

        LOGGER.info("New task created: {} by user: {}", id, username);
        return ResponseEntity.ok(new NewTaskResponse(id.toString()));
    }

    public record MarkDoneRequest(UUID taskid) { }
    public record MarkDoneResponse(boolean success) { }
    public record NewTaskResponse(String taskid) { }
    public record NewTaskRequest(Name name, Description desc, FutureDate dueDate, ValidTime dueTime, java.util.List<String> responsibilityOf) { }


    @PreAuthorize("@taskAuthorization.fileIsInOwnedOrAssignedTask(#filename, authentication)")
    @GetMapping("/attachment")
    public ResponseEntity<byte[]> getAttachment(@RequestParam(name = "file") String filename) throws IOException {
        Path uploadsDir = Path.of("uploads").toAbsolutePath();
        Path attachmentPath = uploadsDir.resolve(filename).normalize();

        if (!attachmentPath.startsWith(uploadsDir)) {
            LOGGER.warn("Path traversal attempt: {}", filename);
            throw new IllegalArgumentException("Path traversal attempt: " + filename);
        }

        if (!Files.isReadable(attachmentPath) || Files.isDirectory(attachmentPath)) {
            LOGGER.warn("Attachment not found or is a directory: {}", attachmentPath);
            throw new java.io.FileNotFoundException("Attachment not found or is not a file: " + filename);
        }

        byte[] fileBytes = Files.readAllBytes(attachmentPath);
        
        // Determine content type based on file extension
        String lowerFilename = filename.toLowerCase();
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if (lowerFilename.endsWith(".pdf")) {
            contentType = MediaType.APPLICATION_PDF_VALUE;
        } else if (lowerFilename.endsWith(".doc")) {
            contentType = "application/msword";
        } else if (lowerFilename.endsWith(".docx")) {
            contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(fileBytes);
    }
}