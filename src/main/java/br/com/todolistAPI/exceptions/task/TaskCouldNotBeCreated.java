package br.com.todolistAPI.exceptions.task;

public class TaskCouldNotBeCreated extends RuntimeException {
    public TaskCouldNotBeCreated(String message) {
        super(message);
    }

    public TaskCouldNotBeCreated(String message, Throwable cause) {
        super(message, cause);
    }
}
