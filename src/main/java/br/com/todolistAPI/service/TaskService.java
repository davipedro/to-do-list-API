package br.com.todolistAPI.service;

import br.com.todolistAPI.task.Task;
import br.com.todolistAPI.task.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private TaskRepository taskRepository;
    public List<Task> getAllTasks(){
       return taskRepository.findAll();
    }

    public Optional<Task> getByTitle(String title){
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<Task> getByCreationDate(LocalDate creationDate){
        return taskRepository.findByCreationDate(creationDate);
    }
}
