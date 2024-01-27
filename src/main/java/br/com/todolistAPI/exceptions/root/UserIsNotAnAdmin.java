package br.com.todolistAPI.exceptions.root;

public class UserIsNotAnAdmin extends RuntimeException{
    public UserIsNotAnAdmin(String message) {
        super(message);
    }
}
