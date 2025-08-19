package com.example.todoapp.controller;


import com.example.todoapp.model.Task;
import com.example.todoapp.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService tasks;

    public TaskController(TaskService tasks) { this.tasks = tasks; }

    @GetMapping
    public List<Task> myTasks(Principal principal) {
        return tasks.listMyTasks(principal.getName());
    }

    @GetMapping("/{id}")
    public Task getOne(@PathVariable Long id, Principal principal) {
        return tasks.getMyTask(principal.getName(), id);
    }

    @PostMapping
    public Task create(@RequestBody Task task, Principal principal) {
        return tasks.createMyTask(principal.getName(), task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task, Principal principal) {
        return tasks.updateMyTask(principal.getName(), id, task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        tasks.deleteMyTask(principal.getName(), id);
    }
}
