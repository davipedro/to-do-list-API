package br.com.todolistAPI.exceptions;

public class TaskNotFoundException extends Throwable{
    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
