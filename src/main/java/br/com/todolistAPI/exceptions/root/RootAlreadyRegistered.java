package br.com.todolistAPI.exceptions.root;

public class RootAlreadyRegistered extends RuntimeException{
    public RootAlreadyRegistered(String message) {
        super(message);
    }
}
