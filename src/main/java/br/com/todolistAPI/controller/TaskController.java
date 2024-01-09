package br.com.todolistAPI.controller;

import br.com.todolistAPI.service.TaskService;
import br.com.todolistAPI.task.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    TaskService taskService = new TaskService();

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{title}")
    public Task getByTitle(@PathVariable String title){
        Optional<Task> task = taskService.getByTitle(title);
        return task.orElseThrow();
    }

    @GetMapping("/{creationDate}")
    public Task getByCreationDate(@PathVariable LocalDate creationDate){
        Optional<Task> task = taskService.getByCreationDate(creationDate);
        return task.orElseThrow();
    }
}
