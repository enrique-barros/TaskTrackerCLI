/**
 * Utility class for reading and writing tasks to a JSON file.
 * Handles JSON serialization and deserialization of Task objects.
 */


package com.tasktracker.util;

import com.tasktracker.model.Task;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonUtil {

    public static final String FILE_NAME = "tasks.json";

    public static void initializeFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Files.writeString(file.toPath(), "[]");
                }
            } catch (IOException e) {
                System.out.println("Error creating tasks.json: " + e.getMessage());
            }
        }
    }

    public static List<Task> readTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            String content = Files.readString(Paths.get(FILE_NAME));
            if (content.isEmpty() || content.equals("[]")) {
                return tasks;
            }

            content = content.substring(1, content.length() - 1);
            String[] entries = content.split("},\\{");

            for (String entry : entries) {
                entry = entry.replace("{", "").replace("}", "");
                String[] fields = entry.split(",");
                int id = 0;
                String description = "";
                String status = "";
                java.time.LocalDateTime createdAt = null;
                java.time.LocalDateTime updatedAt = null;

                for (String field : fields) {
                    String[] kv = field.split(":", 2);
                    String key = kv[0].trim().replace("\"", "");
                    String value = kv[1].trim().replace("\"", "");
                    switch (key) {
                        case "id" -> id = Integer.parseInt(value);
                        case "description" -> description = value;
                        case "status" -> status = value;
                        case "createdAt" -> createdAt = java.time.LocalDateTime.parse(value);
                        case "updatedAt" -> updatedAt = java.time.LocalDateTime.parse(value);
                    }
                }
                tasks.add(new Task(id, description, status, createdAt, updatedAt));
            }
        } catch (IOException e) {
            System.out.println("Error reading tasks.json: " + e.getMessage());
        }
        return tasks;
    }

    public static void writeTasks(List<Task> tasks) {
        List<String> jsonTasks = new ArrayList<>();
        for (Task task : tasks) {
            jsonTasks.add(taskToJson(task));
        }
        String content = "[" + String.join(",", jsonTasks) + "]";
        try {
            Files.writeString(Paths.get(FILE_NAME), content);
        } catch (IOException e) {
            System.out.println("Error writing tasks.json: " + e.getMessage());
        }
    }

    public static Task parseTask(String json) {
        json = json.replace("{", "").replace("}", "");
        String[] fields = json.split(",");
        int id = 0;
        String description = "";
        String status = "";
        java.time.LocalDateTime createdAt = null;
        java.time.LocalDateTime updatedAt = null;

        for (String field : fields) {
            String[] kv = field.split(":", 2);
            String key = kv[0].trim().replace("\"", "");
            String value = kv[1].trim().replace("\"", "");
            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "description" -> description = value;
                case "status" -> status = value;
                case "createdAt" -> createdAt = java.time.LocalDateTime.parse(value);
                case "updatedAt" -> updatedAt = java.time.LocalDateTime.parse(value);
            }
        }

        return new Task(id, description, status, createdAt, updatedAt);
    }

    public static String taskToJson(Task task) {
        return "{" +
                "\"id\":" + task.getId() + "," +
                "\"description\":\"" + task.getDescription() + "\"," +
                "\"status\":\"" + task.getStatus() + "\"," +
                "\"createdAt\":\"" + task.getCreatedAt() + "\"," +
                "\"updatedAt\":\"" + task.getUpdatedAt() + "\"" +
                "}";
    }


}

