package br.com.todolistAPI.exceptions;

import br.com.todolistAPI.exceptions.task.TaskCouldNotBeCreated;
import br.com.todolistAPI.exceptions.task.TaskNotFoundException;
import br.com.todolistAPI.exceptions.user.AlreadyRegisteredUserException;
import br.com.todolistAPI.exceptions.user.RoleNotFoundException;
import br.com.todolistAPI.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    private ResponseEntity<String> taskNotFoundHandler(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }
    @ExceptionHandler(SQLException.class)
    private ResponseEntity<String> duplicatedTaskHandler(){
        return ResponseEntity.badRequest().body("A task with this name already exist");
    }
    @ExceptionHandler(AlreadyRegisteredUserException.class)
    private ResponseEntity<String> RegisteredUserHandler(){
        return ResponseEntity.badRequest().body("User already registered");
    }
    @ExceptionHandler(RoleNotFoundException.class)
    private ResponseEntity<String> RoleNotFoundHandler(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
    }
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<String> UserNotFoundHandler(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @ExceptionHandler(TaskCouldNotBeCreated.class)
    private ResponseEntity<String> TaskCreationException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User could not be created");
    }
}
