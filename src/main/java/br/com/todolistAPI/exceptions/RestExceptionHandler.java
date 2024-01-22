package br.com.todolistAPI.exceptions;

import br.com.todolistAPI.exceptions.task.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    private ResponseEntity<String> taskNotFoundHandler(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }
}
