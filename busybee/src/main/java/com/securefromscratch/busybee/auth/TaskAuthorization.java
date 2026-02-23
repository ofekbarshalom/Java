package com.securefromscratch.busybee.auth;

import com.securefromscratch.busybee.safety.ImageName;
import com.securefromscratch.busybee.storage.TaskComment;
import com.securefromscratch.busybee.storage.Task;
import com.securefromscratch.busybee.storage.TasksStorage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.securefromscratch.busybee.controllers.TaskOut;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class TaskAuthorization {
    private final TasksStorage m_tasks;

    public TaskAuthorization(TasksStorage tasks) {
        this.m_tasks = tasks;
    }

    public boolean isAuthorizedToCreate(Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role) || "ROLE_CREATOR".equals(role)) {
                return true;
            }
            if ("ROLE_TRIAL".equals(role)) {
                List<Task> userTasks = m_tasks.getAll().stream()
                        .filter(t -> t.createdBy().equals(username))
                        .toList();
                return userTasks.isEmpty() || userTasks.stream().allMatch(Task::done);
            }
        }
        return false;
    }

    public boolean userAllowedToViewTask(TaskOut t, Authentication authentication) {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(auth.getAuthority())) {
                return true;
            }
        }
        String username = authentication.getName();
        if (t.createdBy().equals(username)) {
            return true;
        }
        for (String responsible : t.responsibilityOf()) {
            if (responsible.equals(username)) {
                return true;
            }
        }
        return false;
    }

        public boolean userAllowedToViewTask(Task t, Authentication authentication) {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(auth.getAuthority())) {
                return true;
            }
        }
        String username = authentication.getName();
        if (t.createdBy().equals(username)) {
            return true;
        }
        for (String responsible : t.responsibilityOf()) {
            if (responsible.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTaskCreator(UUID taskid, Authentication authentication) {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(auth.getAuthority())) {
                return true;
            }
        }
        String username = authentication.getName();
        return m_tasks.find(taskid)
                .map(task -> task.createdBy().equals(username))
                .orElse(false);
    }

    /**
     * Check if user can close a task.
     * Allowed: ADMIN, OWNER (creator), or users in responsibilityOf
     */
    public boolean canCloseTask(UUID taskid, Authentication authentication) {
        // ADMIN can always close tasks
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if ("ROLE_ADMIN".equals(auth.getAuthority())) {
                return true;
            }
        }
        
        String username = authentication.getName();
        return m_tasks.find(taskid)
                .map(task -> {
                    // Owner can close
                    if (task.createdBy().equals(username)) {
                        return true;
                    }
                    // Users in responsibilityOf can close
                    for (String responsible : task.responsibilityOf()) {
                        if (responsible.equals(username)) {
                            return true;
                        }
                    }
                    return false;
                })
                .orElse(false);
    }

    public boolean imgIsInOwnedOrAssignedTask(ImageName imgName, Authentication authentication) {
        if (imgName == null || imgName.getName() == null || imgName.getName().isBlank()) {
            return false;
        }
        for (Task t : m_tasks.getAll()) {
            if (!userAllowedToViewTask(t, authentication)) {
                continue;
            }
            for (TaskComment c : t.comments()) {
                if (c.image().isPresent() && c.image().get().equals(imgName.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean fileIsInOwnedOrAssignedTask(String filename, Authentication authentication) {
        if (filename == null || filename.isBlank()) {
            return false;
        }
        for (Task t : m_tasks.getAll()) {
            if (!userAllowedToViewTask(t, authentication)) {
                continue;
            }
            for (TaskComment c : t.comments()) {
                if (c.attachment().isPresent() && c.attachment().get().equals(filename)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAllowedToComment(UUID taskid, Authentication authentication) {
        if (taskid == null) {
            return false;
        }
        return m_tasks.find(taskid)
                .map(task -> userAllowedToViewTask(task, authentication))
                .orElse(false);
    }
}
