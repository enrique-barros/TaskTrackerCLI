package com.tasktracker.util;

import com.tasktracker.model.Task;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonUtilTest {

    private static final String TEST_FILE = "tasks.json";

    @BeforeAll
    void setup() {
        JsonUtil.initializeFile();
    }

    @AfterEach
    void cleanup() throws Exception {
        Files.writeString(new File(TEST_FILE).toPath(), "[]");
    }

    @Test
    void testInitializeFile_createsFile() {
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "tasks.json should exist after initialization");
    }

    @Test
    void testWriteAndReadTasks() {
        Task task1 = new Task(1, "Task 1", "todo", LocalDateTime.now(), LocalDateTime.now());
        Task task2 = new Task(2, "Task 2", "in-progress", LocalDateTime.now(), LocalDateTime.now());

        JsonUtil.writeTasks(List.of(task1, task2));
        List<Task> readTasks = JsonUtil.readTasks();

        assertEquals(2, readTasks.size());
        assertEquals(task1.getDescription(), readTasks.get(0).getDescription());
        assertEquals(task2.getStatus(), readTasks.get(1).getStatus());
    }

    @Test
    void testTaskToJsonAndParseTask() {
        Task original = new Task(5, "Test JSON", "done", LocalDateTime.now(), LocalDateTime.now());
        String json = JsonUtil.taskToJson(original);
        Task parsed = JsonUtil.parseTask(json);

        assertEquals(original.getId(), parsed.getId());
        assertEquals(original.getDescription(), parsed.getDescription());
        assertEquals(original.getStatus(), parsed.getStatus());
        assertEquals(original.getCreatedAt().withNano(0), parsed.getCreatedAt().withNano(0));
        assertEquals(original.getUpdatedAt().withNano(0), parsed.getUpdatedAt().withNano(0));
    }

    @Test
    void testReadTasks_emptyFileReturnsEmptyList() throws Exception {
        Files.writeString(new File(TEST_FILE).toPath(), "[]");
        List<Task> tasks = JsonUtil.readTasks();
        assertTrue(tasks.isEmpty(), "Reading an empty file should return an empty list");
    }
}
