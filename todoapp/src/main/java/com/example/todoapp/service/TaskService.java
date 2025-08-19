package com.example.todoapp.service;


import com.example.todoapp.exception.ResourceNotFoundException;
import com.example.todoapp.model.Task;
import com.example.todoapp.model.User;
import com.example.todoapp.repository.TaskRepository;
import com.example.todoapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository tasks;
    private final UserRepository users;

    public TaskService(TaskRepository tasks, UserRepository users) {
        this.tasks = tasks;
        this.users = users;
    }

    private User requireUser(String username) {
        return users.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found with userName: " + username));
    }

    public List<Task> listMyTasks(String username) {
        return tasks.findByOwner(requireUser(username));
    }

    public Task getMyTask(String username, Long id) {
        return tasks.findByIdAndOwner(id, requireUser(username))
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public Task createMyTask(String username, Task t) {
        t.setId(null);
        t.setOwner(requireUser(username));
        return tasks.save(t);
    }

    public Task updateMyTask(String username, Long id, Task updated) {
        Task existing = getMyTask(username, id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCompleted(updated.isCompleted());
        return tasks.save(existing);
    }

    public void deleteMyTask(String username, Long id) {
        Task existing = getMyTask(username, id);
        tasks.delete(existing);
    }
}