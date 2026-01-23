# Task Tracker CLI

**Task Tracker** is a command-line application built in **Java** to help you track and manage your tasks efficiently. With this CLI tool, you can keep track of what you need to do, what you are currently working on, and what you have completed.

## Features

- Add, update, and delete tasks
- Mark tasks as "in-progress" or "done"
- List all tasks or filter tasks by status (`todo`, `in-progress`, `done`)
- Tasks are stored in a JSON file for persistence
- Includes automated tests for key functionality

## Task Properties

Each task has the following properties:

- **id**: A unique identifier for the task
- **description**: A short description of the task
- **status**: The status of the task (`todo`, `in-progress`, `done`)
- **createdAt**: Date and time when the task was created
- **updatedAt**: Date and time when the task was last updated

## Installation and Setup

1. Make sure you have **Java** installed (version 11 or higher recommended)
2. Clone the repository:  
   `git clone <repository-url>`
3. Navigate to the project directory:  
   `cd task-tracker-cli`
4. Compile the Java files:  
   `javac -d bin $(find src/main/java -name "*.java")`

## Usage

Run the application from the command line using:  
`java -cp bin com.tasktracker.Main <command> [arguments]`

### Commands

- Add a new task:  
  `java -cp bin com.tasktracker.Main add "Buy groceries"`
- Update a task:  
  `java -cp bin com.tasktracker.Main update 1 "Buy groceries and cook dinner"`
- Delete a task:  
  `java -cp bin com.tasktracker.Main delete 1`
- Mark a task as in-progress:  
  `java -cp bin com.tasktracker.Main mark-in-progress 1`
- Mark a task as done:  
  `java -cp bin com.tasktracker.Main mark-done 1`
- List all tasks:  
  `java -cp bin com.tasktracker.Main list`
- List tasks by status:  
  `java -cp bin com.tasktracker.Main list done`  
  `java -cp bin com.tasktracker.Main list todo`  
  `java -cp bin com.tasktracker.Main list in-progress`

## Project Structure

```text
├───main
│   ├───java
│   │   └───com
│   │       └───tasktracker
│   │           │   Main.java
│   │           │   
│   │           ├───cli
│   │           │       ConsoleInterface.java
│   │           │       
│   │           ├───model
│   │           │       Task.java
│   │           │       
│   │           ├───service
│   │           │       TaskService.java
│   │           │       
│   │           └───util
│   │                   JsonUtil.java
│   │                   
│   └───resources
└───test
    └───java
        └───com
            └───tasktracker
                ├───model
                │       TaskTest.java
                │       
                ├───service
                │       TaskServiceTest.java
                │       
                └───util
                        JsonUtilTest.java
```

## Testing

Tests are written using **JUnit** and are located under `src/test/java`, following the same package structure as the main code.

You can run the tests using your preferred IDE (IntelliJ, Eclipse, etc.) or a build tool like Maven or Gradle.

No special commands are required; simply execute the test classes from your IDE or test runner.

## Contributing

If you want to contribute to Task Tracker CLI, follow these steps:

1. Fork the repository
2. Create a new branch for your feature or bugfix
3. Commit your changes
4. Push to your branch
5. Open a pull request

# Task Tracker CLI

**Task Tracker** is a command-line application built in **Java** to help you track and manage your tasks efficiently.

For more details, see:

- [Architecture](ARCHITECTURE.md) 
- [Testing](TESTING.md)

## License

This project is licensed under the MIT License.
