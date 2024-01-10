package br.com.todolistAPI.controller;

import br.com.todolistAPI.service.TaskService;
import br.com.todolistAPI.task.Task;
import br.com.todolistAPI.task.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService = new TaskService();

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        if(tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Task> getByTitle(@PathVariable String title){
        Optional<Task> taskByTitle = taskService.getByTitle(title);
        Task task = taskByTitle.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found"));
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/{creationDate}")
    public ResponseEntity<Task> getByCreationDate(@PathVariable LocalDate creationDate){
        Optional<Task> taskByCreationDate = taskService.getByCreationDate(creationDate);
        Task task = taskByCreationDate.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found"));
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id){
        try {
            taskService.deleteTask(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("Deleted successfully");
    }

    @PostMapping("/")
    public ResponseEntity<Object> createTask(@RequestBody @Valid TaskDTO taskDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "The task could not be created \n Validation error:" + errors);
        }
        taskService.createTask(taskDTO);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping
    public ResponseEntity<String> patchTask(@RequestBody @Valid TaskDTO taskDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "The task could not be updated \n Validation error:" + errors);
        }
        taskService.patchTask(taskDTO);
        return ResponseEntity.ok("Updated successfully");
    }

    @PutMapping
    public ResponseEntity<String> putTask(@RequestBody @Valid TaskDTO taskDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "The task could not be updated \n Validation error:" + errors);
        }
        taskService.putTask(taskDTO);
        return ResponseEntity.ok("Updated successfully");
    }


}
