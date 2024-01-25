package br.com.todolistAPI.exceptions.user;

public class AlreadyRegisteredUserException extends RuntimeException {
    public AlreadyRegisteredUserException(String message) {
        super(message);
    }

    public AlreadyRegisteredUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
