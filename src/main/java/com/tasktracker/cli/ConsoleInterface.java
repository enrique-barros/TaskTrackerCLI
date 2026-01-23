/**
 * Command-line interface (CLI) for interacting with the Task Tracker application.
 * Provides a menu-driven interface for task management, including adding,
 * updating, deleting, marking, and listing tasks.
 */

package com.tasktracker.cli;

import com.tasktracker.model.Task;
import com.tasktracker.service.TaskService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {

    private final TaskService taskService;
    private final Scanner scanner;

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";

    public ConsoleInterface() {
        taskService = new TaskService();
        scanner = new Scanner(System.in);
    }

    public void runMenu() {
        System.out.println(BLUE + "====================================" + RESET);
        System.out.println(PURPLE + "      WELCOME TO TASK TRACKER CLI   " + RESET);
        System.out.println(BLUE + "====================================" + RESET);

        boolean running = true;

        while (running) {
            printMenu();
            System.out.print(YELLOW + "Choose an option: " + RESET);
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> handleAdd();
                case "2" -> handleUpdate();
                case "3" -> handleDelete();
                case "4" -> handleMarkInProgress();
                case "5" -> handleMarkDone();
                case "6" -> handleListAll();
                case "7" -> handleListByStatus();
                case "0" -> {
                    running = false;
                    System.out.println(GREEN + "Exiting Task Tracker. Goodbye!" + RESET);
                }
                default -> System.out.println(RED + "Invalid option. Try again." + RESET);
            }
            System.out.println(); // blank line for readability
        }
    }

    private void printMenu() {
        System.out.println(BLUE + "--- Task Tracker Menu ---" + RESET);
        System.out.println("1. Add Task");
        System.out.println("2. Update Task Description");
        System.out.println("3. Delete Task");
        System.out.println("4. Mark Task In-Progress");
        System.out.println("5. Mark Task Done");
        System.out.println("6. List All Tasks");
        System.out.println("7. List Tasks by Status");
        System.out.println("0. Exit");
    }

    private void handleAdd() {
        System.out.print(YELLOW + "Enter task description: " + RESET);
        String desc = scanner.nextLine().trim();
        Task task = taskService.addTask(desc);
        System.out.println(GREEN + "Task added:\n" + formatTask(task) + RESET);
    }

    private void handleUpdate() {
        System.out.print(YELLOW + "Enter task ID to update: " + RESET);
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print(YELLOW + "Enter new description: " + RESET);
        String desc = scanner.nextLine().trim();
        taskService.updateTask(id, desc);

        Task task = taskService.getAllTasks().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
        if (task != null) {
            System.out.println(GREEN + "Task updated:\n" + formatTask(task) + RESET);
        } else {
            System.out.println(RED + "Task not found." + RESET);
        }
    }

    private void handleDelete() {
        System.out.print(YELLOW + "Enter task ID to delete: " + RESET);
        int id = Integer.parseInt(scanner.nextLine().trim());
        taskService.deleteTask(id);
        System.out.println(GREEN + "Task deleted if it existed." + RESET);
    }

    private void handleMarkInProgress() {
        System.out.print(YELLOW + "Enter task ID to mark in-progress: " + RESET);
        int id = Integer.parseInt(scanner.nextLine().trim());
        taskService.markInProgress(id);

        Task task = taskService.getAllTasks().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
        if (task != null) {
            System.out.println(GREEN + "Task marked in-progress:\n" + formatTask(task) + RESET);
        } else {
            System.out.println(RED + "Task not found." + RESET);
        }
    }

    private void handleMarkDone() {
        System.out.print(YELLOW + "Enter task ID to mark done: " + RESET);
        int id = Integer.parseInt(scanner.nextLine().trim());
        taskService.markDone(id);

        Task task = taskService.getAllTasks().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
        if (task != null) {
            System.out.println(GREEN + "Task marked done:\n" + formatTask(task) + RESET);
        } else {
            System.out.println(RED + "Task not found." + RESET);
        }
    }

    private void handleListAll() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println(RED + "No tasks found." + RESET);
        } else {
            tasks.forEach(task -> System.out.println(BLUE + formatTask(task) + RESET));
        }
    }

    private void handleListByStatus() {
        System.out.print(YELLOW + "Enter status (todo, in-progress, done): " + RESET);
        String status = scanner.nextLine().trim();
        List<Task> tasks = taskService.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            System.out.println(RED + "No tasks with status '" + status + "'." + RESET);
        } else {
            tasks.forEach(task -> System.out.println(BLUE + formatTask(task) + RESET));
        }
    }

    private String formatTask(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Task ID: " + task.getId() + "\n" +
                "Description: " + task.getDescription() + "\n" +
                "Status: " + task.getStatus() + "\n" +
                "Created At: " + task.getCreatedAt().format(formatter) + "\n" +
                "Updated At: " + task.getUpdatedAt().format(formatter) + "\n";
    }
}
