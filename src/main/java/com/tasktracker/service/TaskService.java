package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class TaskService {

    // Final list storing tasks in memory
    private final List<Task> tasks = new ArrayList<>();

    // Constructor loads tasks from JSON
    public TaskService() {
        JsonUtil.initializeFile();
        tasks.addAll((List<Task>) JsonUtil.readTasks());
    }

    // Generate next unique ID
    private int generateNextId() {
        int maxId = 0;
        for (Task task : tasks) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }
        return maxId + 1;
    }

    // Find task by ID
    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    // Add a new task
    public Task addTask(String description) {
        int id = generateNextId();
        Task task = new Task(id, description, "todo", LocalDateTime.now(), LocalDateTime.now());
        tasks.add(task);
        JsonUtil.writeTasks(tasks);
        return task;
    }


    // Update task description
    public void updateTask(int id, String newDescription) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setDescription(newDescription);
            task.setUpdatedAt(LocalDateTime.now());
            JsonUtil.writeTasks(tasks);
        }
    }

    // Delete a task
    public void deleteTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            JsonUtil.writeTasks(tasks);
        }
    }

    // Mark task in progress
    public void markInProgress(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.markInProgress();
            JsonUtil.writeTasks(tasks);
        }
    }

    // Mark task done
    public void markDone(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.markDone();
            JsonUtil.writeTasks(tasks);
        }
    }

    // List all tasks
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    // List tasks by status
    public List<Task> getTasksByStatus(String status) {
        List<Task> filtered = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase(status)) {
                filtered.add(task);
            }
        }
        return filtered;
    }
}
