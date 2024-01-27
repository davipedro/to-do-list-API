package br.com.todolistAPI.exceptions.admin;

public class UserDeletionException extends RuntimeException{
    public UserDeletionException(String message) {
        super(message);
    }
}
