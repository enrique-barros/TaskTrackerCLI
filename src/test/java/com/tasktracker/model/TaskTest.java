package com.tasktracker.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void constructorSetsAllFieldsCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        Task task = new Task(1, "Test task", "todo", now, now);

        assertEquals(1, task.getId());
        assertEquals("Test task", task.getDescription());
        assertEquals("todo", task.getStatus());
        assertEquals(now, task.getCreatedAt());
        assertEquals(now, task.getUpdatedAt());
    }

    @Test
    void getIdReturnsCorrectId() {
        Task task = new Task(5, "Task", "todo", LocalDateTime.now(), LocalDateTime.now());
        assertEquals(5, task.getId());
    }

    @Test
    void getDescriptionReturnsCorrectDescription() {
        Task task = new Task(1, "My description", "todo", LocalDateTime.now(), LocalDateTime.now());
        assertEquals("My description", task.getDescription());
    }

    @Test
    void getStatusReturnsCorrectStatus() {
        Task task = new Task(1, "Task", "in-progress", LocalDateTime.now(), LocalDateTime.now());
        assertEquals("in-progress", task.getStatus());
    }

    @Test
    void getCreatedAtReturnsCorrectValue() {
        LocalDateTime createdAt = LocalDateTime.now();
        Task task = new Task(1, "Task", "todo", createdAt, createdAt);
        assertEquals(createdAt, task.getCreatedAt());
    }

    @Test
    void getUpdatedAtReturnsCorrectValue() {
        LocalDateTime updatedAt = LocalDateTime.now();
        Task task = new Task(1, "Task", "todo", updatedAt, updatedAt);
        assertEquals(updatedAt, task.getUpdatedAt());
    }

    @Test
    void setDescriptionUpdatesDescription() {
        Task task = new Task(1, "Old description", "todo",
                LocalDateTime.now(), LocalDateTime.now());

        task.setDescription("New description");

        assertEquals("New description", task.getDescription());
    }

    @Test
    void setUpdatedAtUpdatesUpdatedAt() {
        LocalDateTime initialTime = LocalDateTime.now();
        Task task = new Task(1, "Task", "todo", initialTime, initialTime);

        LocalDateTime newTime = initialTime.plusMinutes(10);
        task.setUpdatedAt(newTime);

        assertEquals(newTime, task.getUpdatedAt());
    }

    @Test
    void markInProgressSetsStatusCorrectly() {
        Task task = new Task(1, "Task", "todo",
                LocalDateTime.now(), LocalDateTime.now());

        task.markInProgress();

        assertEquals("in-progress", task.getStatus());
    }

    @Test
    void markInProgressUpdatesUpdatedAt() {
        Task task = new Task(1, "Task", "todo",
                LocalDateTime.now(), LocalDateTime.now());

        LocalDateTime before = task.getUpdatedAt();
        task.markInProgress();

        assertTrue(task.getUpdatedAt().isAfter(before)
                || task.getUpdatedAt().isEqual(before));
    }

    @Test
    void markDoneSetsStatusCorrectly() {
        Task task = new Task(1, "Task", "in-progress",
                LocalDateTime.now(), LocalDateTime.now());

        task.markDone();

        assertEquals("done", task.getStatus());
    }

    @Test
    void markDoneUpdatesUpdatedAt() {
        Task task = new Task(1, "Task", "in-progress",
                LocalDateTime.now(), LocalDateTime.now());

        LocalDateTime before = task.getUpdatedAt();
        task.markDone();

        assertTrue(task.getUpdatedAt().isAfter(before)
                || task.getUpdatedAt().isEqual(before));
    }
}
