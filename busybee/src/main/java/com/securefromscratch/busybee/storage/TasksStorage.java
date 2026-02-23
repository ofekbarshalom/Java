package com.securefromscratch.busybee.storage;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class TasksStorage {
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksStorage.class);
    private final List<Task> m_tasks = loadTasks();

    public TasksStorage() throws IOException, ClassNotFoundException {
        // Use initial hardcoded values if the file does not exist or is empty
        if (m_tasks.isEmpty()) {
            InitialDataGenerator.fillWithData(m_tasks);
        }
    }

    public List<Task> getAll() {
        return Collections.unmodifiableList(m_tasks);
    }

    public UUID add(String name, String desc, String createdBy, String[] responsibilityOf) throws IOException {
        Task newTask = new Task(name, desc, createdBy, responsibilityOf);
        return add(newTask);
    }

    public UUID add(String name, String desc, LocalDate dueDate, String createdBy, String[] responsibilityOf) throws IOException {
        Task newTask = new Task(name, desc, dueDate, createdBy, responsibilityOf);
        return add(newTask);
    }

    public UUID add(String name, String desc, LocalDate dueDate, LocalTime dueTime, String createdBy, String[] responsibilityOf) throws IOException {
        Task newTask = new Task(name, desc, dueDate, dueTime, createdBy, responsibilityOf);
        return add(newTask);
    }

    public UUID add(Task newTask) throws IOException {
        m_tasks.add(newTask);
        try {
            saveTasks();
        } catch (IOException e) {
            m_tasks.remove(newTask); // undo
            throw e;
        }
        return newTask.taskid();
    }

    public boolean markDone(UUID taskid) throws IOException {
        Iterator<Task> tasksItr = m_tasks.iterator();
        while (tasksItr.hasNext()) {
            Task t = tasksItr.next();
            if (t.taskid().equals(taskid)) {
                if (t.done()) {
                    return true;
                }
                tasksItr.remove();
                Task doneTask = Task.asDone(t);
                m_tasks.add(doneTask);
                try {
                    saveTasks();
                } catch (IOException e) {
                    m_tasks.remove(doneTask);
                    m_tasks.add(t);
                    throw e;
                }
                return false;
            }
        }
        throw new TaskNotFoundException(taskid);
    }

    private static final String TASKS_FILE = "tasks.dat";

    private List<Task> loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASKS_FILE))) {
            LOGGER.info("Tasks loaded from file: {}", TASKS_FILE);
            return (List<Task>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, which is okay on first run.
            LOGGER.info("Tasks file not found, starting with empty list");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Error loading tasks from file: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private void saveTasks() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TASKS_FILE))) {
            oos.writeObject(m_tasks);
            LOGGER.debug("Tasks saved to file: {} (total tasks: {})", TASKS_FILE, m_tasks.size());
        }
    }

    public UUID addComment(Task t, String text, String createdBy, Optional<UUID> after) throws IOException {
        UUID commentId = t.addComment(text, createdBy, after);
        try {
            saveTasks();
        } catch (IOException e) {
            t.removeComment(commentId);
            throw e;
        }
        return commentId;
    }

    public UUID addComment(Task t, String text, Optional<String> image, Optional<String> attachment, String createdBy, Optional<UUID> after) throws IOException {
        UUID commentId = t.addComment(text, image, attachment, createdBy, after);
        try {
            saveTasks();
        } catch (IOException e) {
            t.removeComment(commentId);
            throw e;
        }
        return commentId;
    }

    public Optional<Task> find(UUID taskid) {
        return m_tasks.stream().filter((other)->other.taskid().equals(taskid)).findAny();
    }
}
