package com.tasktracker;

import com.tasktracker.service.TaskService;

public class Main {
    public static void main(String[] args) {
        TaskService service = new TaskService();

        if (args.length == 0) {
            System.out.println("No command provided.");
            return;
        }

        String command = args[0];
        switch (command) {
            case "add" -> {
                if (args.length < 2) {
                    System.out.println("Description required for add.");
                } else {
                    String description = args[1];
                    System.out.println("Task added: " + service.addTask(description));
                }
            }
            case "update" -> {
                if (args.length < 3) {
                    System.out.println("ID and new description required for update.");
                } else {
                    int id = Integer.parseInt(args[1]);
                    String newDesc = args[2];
                    service.updateTask(id, newDesc);
                    System.out.println("Task updated.");
                }
            }
            case "delete" -> {
                if (args.length < 2) {
                    System.out.println("ID required for delete.");
                } else {
                    int id = Integer.parseInt(args[1]);
                    service.deleteTask(id);
                    System.out.println("Task deleted.");
                }
            }
            case "mark-in-progress" -> {
                if (args.length < 2) {
                    System.out.println("ID required for mark-in-progress.");
                } else {
                    int id = Integer.parseInt(args[1]);
                    service.markInProgress(id);
                    System.out.println("Task marked in progress.");
                }
            }
            case "mark-done" -> {
                if (args.length < 2) {
                    System.out.println("ID required for mark-done.");
                } else {
                    int id = Integer.parseInt(args[1]);
                    service.markDone(id);
                    System.out.println("Task marked done.");
                }
            }
            case "list" -> {
                if (args.length == 1) {
                    service.getAllTasks().forEach(System.out::println);
                } else {
                    String status = args[1];
                    service.getTasksByStatus(status).forEach(System.out::println);
                }
            }
            default -> System.out.println("Unknown command: " + command);
        }
    }
}
