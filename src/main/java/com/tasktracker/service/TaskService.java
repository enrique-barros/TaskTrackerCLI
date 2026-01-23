/**
 * Service class responsible for managing tasks in memory.
 * Supports adding, updating, deleting, marking tasks, and retrieving tasks by status.
 */

package com.tasktracker.service;

import com.tasktracker.model.Task;
import com.tasktracker.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public TaskService() {
        JsonUtil.initializeFile();
        tasks.addAll((List<Task>) JsonUtil.readTasks());
    }

    private int generateNextId() {
        int maxId = 0;
        for (Task task : tasks) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }
        return maxId + 1;
    }

    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public Task addTask(String description) {
        int id = generateNextId();
        Task task = new Task(id, description, "todo", LocalDateTime.now(), LocalDateTime.now());
        tasks.add(task);
        JsonUtil.writeTasks(tasks);
        return task;
    }


    public void updateTask(int id, String newDescription) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setDescription(newDescription);
            task.setUpdatedAt(LocalDateTime.now());
            JsonUtil.writeTasks(tasks);
        }
    }

    public void deleteTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            tasks.remove(task);
            JsonUtil.writeTasks(tasks);
        }
    }

    public void markInProgress(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.markInProgress();
            JsonUtil.writeTasks(tasks);
        }
    }

    public void markDone(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.markDone();
            JsonUtil.writeTasks(tasks);
        }
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

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
