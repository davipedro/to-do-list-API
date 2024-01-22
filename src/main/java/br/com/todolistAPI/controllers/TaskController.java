package br.com.todolistAPI.controllers;

import br.com.todolistAPI.services.TaskService;
import br.com.todolistAPI.domain.task.Task;
import br.com.todolistAPI.domain.task.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/title")
    public ResponseEntity<List<Task>> getByTitle(@RequestParam (value = "q") String title){
        List<Task> tasks = taskService.getByTitle(title);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/creation_date")
    public ResponseEntity<List<Task>> getByCreationDate(@RequestParam (value = "q") LocalDate creationDate){
        List<Task> taskByCreationDate = taskService.getByCreationDate(creationDate);
        return ResponseEntity.ok().body(taskByCreationDate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody @Valid TaskDTO taskDTO){
        taskService.createTask(taskDTO);
        return ResponseEntity.status(201).build();
    }


    //procurar sobre tratar excessão passando um ResponseEntity Fernanda Kipper
    @PutMapping("/{taskId}")
    public ResponseEntity<String> putTask(@PathVariable UUID taskId, @RequestBody @Valid TaskDTO taskDTO) {
        taskService.putTask(taskId, taskDTO);
        return ResponseEntity.ok("Updated successfully");
    }


}
