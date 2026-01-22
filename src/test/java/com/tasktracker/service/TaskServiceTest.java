package com.tasktracker.service;

import com.tasktracker.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
        taskService.getAllTasks().forEach(task -> taskService.deleteTask(task.getId()));
    }

    @Test
    void testAddTask() {
        Task task = taskService.addTask("Test task");
        assertNotNull(task);
        assertEquals("Test task", task.getDescription());
        assertEquals("todo", task.getStatus());
        assertTrue(task.getId() > 0);
    }

    @Test
    void testUpdateTask() {
        Task task = taskService.addTask("Initial description");
        taskService.updateTask(task.getId(), "Updated description");
        List<Task> tasks = taskService.getAllTasks();
        assertEquals("Updated description", tasks.get(0).getDescription());
    }

    @Test
    void testDeleteTask() {
        Task task = taskService.addTask("To be deleted");
        taskService.deleteTask(task.getId());
        List<Task> tasks = taskService.getAllTasks();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void testMarkInProgress() {
        Task task = taskService.addTask("Task in progress");
        taskService.markInProgress(task.getId());
        List<Task> tasks = taskService.getAllTasks();
        assertEquals("in-progress", tasks.get(0).getStatus());
    }

    @Test
    void testMarkDone() {
        Task task = taskService.addTask("Task done");
        taskService.markDone(task.getId());
        List<Task> tasks = taskService.getAllTasks();
        assertEquals("done", tasks.get(0).getStatus());
    }

    @Test
    void testGetAllTasks() {
        taskService.addTask("Task 1");
        taskService.addTask("Task 2");
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(2, tasks.size());
    }

    @Test
    void testGetTasksByStatus() {
        taskService.addTask("Task 1"); // todo
        Task t2 = taskService.addTask("Task 2");
        taskService.markInProgress(t2.getId());
        List<Task> inProgressTasks = taskService.getTasksByStatus("in-progress");
        assertEquals(1, inProgressTasks.size());
        assertEquals("in-progress", inProgressTasks.get(0).getStatus());
    }
}
