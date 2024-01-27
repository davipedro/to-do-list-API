package br.com.todolistAPI.controllers;

import br.com.todolistAPI.DTOs.TaskCreateDTO;
import br.com.todolistAPI.DTOs.TaskUpdateDTO;
import br.com.todolistAPI.domain.task.Task;
import br.com.todolistAPI.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tasks")
@Tag(name = "Task Controller", description = "End points para gerenciamento de tarefas")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Cria uma nova tarefa")
    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody @Valid TaskCreateDTO taskCreateDTO) {
        taskService.createTask(taskCreateDTO);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Obtém todas as tarefas")
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Obtém tarefas por título")
    @GetMapping("/title")
    public ResponseEntity<List<Task>> getByTitle(@RequestParam (value = "q") String title){
        List<Task> tasks = taskService.getByTitle(title);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Obtém tarefas por data de criação")
    @GetMapping("/creation_date")
    public ResponseEntity<List<Task>> getByCreationDate(@RequestParam (value = "q") LocalDate creationDate){
        List<Task> taskByCreationDate = taskService.getByCreationDate(creationDate);
        return ResponseEntity.ok().body(taskByCreationDate);
    }

    @Operation(summary = "Atualiza uma tarefa")
    @PutMapping("/{taskId}")
    public ResponseEntity<String> putTask(@PathVariable UUID taskId, @RequestBody @Valid TaskUpdateDTO taskUpdateDTO) {
        taskService.putTask(taskId, taskUpdateDTO);
        return ResponseEntity.ok("Updated successfully");
    }

    @Operation(summary = "Deleta uma tarefa")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Deleted successfully");
    }


}
