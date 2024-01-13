package br.com.todolistAPI.controller;

import br.com.todolistAPI.exceptions.TaskNotFoundException;
import br.com.todolistAPI.service.TaskService;
import br.com.todolistAPI.task.Task;
import br.com.todolistAPI.task.TaskDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TaskService taskService = new TaskService();

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        if(tasks.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/title")
    public ResponseEntity<List<Task>> getByTitle(@RequestParam (value = "q") String title){
        Optional<List<Task>> optionalTasks = taskService.getByTitle(title);
        List<Task> task = optionalTasks.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found"));
        return ResponseEntity.ok(task);
    }

    @GetMapping("/creation_date")
    public ResponseEntity<List<Task>> getByCreationDate(@RequestParam (value = "q") LocalDate creationDate){
        Optional<List<Task>> taskByCreationDate = taskService.getByCreationDate(creationDate);
        List<Task> task = taskByCreationDate.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found"));
        return ResponseEntity.ok().body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id){
        if (taskService.deleteTask(id)){
            return ResponseEntity.ok("Deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody @Valid TaskDTO taskDTO){
        taskService.createTask(taskDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> putTask(@PathVariable UUID taskId, @RequestBody @Valid TaskDTO taskDTO) throws TaskNotFoundException {
        if (taskService.putTask(taskId, taskDTO)){
            return ResponseEntity.ok("Updated successfully");
        }
        return ResponseEntity.notFound().build();
    }


}
