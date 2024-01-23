package br.com.todolistAPI.services;

import br.com.todolistAPI.domain.task.Task;
import br.com.todolistAPI.domain.task.TaskDTO;
import br.com.todolistAPI.exceptions.task.TaskNotFoundException;
import br.com.todolistAPI.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void createTask(TaskDTO taskToSave){
        Task task = new Task(taskToSave, LocalDate.now());
        taskRepository.save(task);
    }

    public List<Task> getAllTasks(){
        List<Task> allTasks = taskRepository.findAll();
        if (allTasks.isEmpty()) throw new TaskNotFoundException("Task(s) do not found");
        return allTasks;
    }

    public List<Task> getByTitle(String title){
        Optional<List<Task>> optionalTasks = taskRepository.findByTitleContainingIgnoreCase(title);
        return optionalTasks
                .orElseThrow(() -> new TaskNotFoundException("Task(s) do not found"));
    }

    public List<Task> getByCreationDate(LocalDate creationDate){
        Optional<List<Task>> optionalTasks = taskRepository.findByCreationDate(creationDate);
        return optionalTasks
                .orElseThrow(() -> new TaskNotFoundException("Task(s) do not found"));
    }

    public void putTask(UUID taskId, TaskDTO taskDTO) {
        Task task =  taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task do not found"));

        if (!(taskDTO.title().isEmpty())) task.setTitle(taskDTO.title());
        if (!(taskDTO.description().isEmpty())) task.setDescription(taskDTO.description());
        task.setConclusionDate(taskDTO.conclusionDate());
        task.setLastUpdate(LocalDate.now());

        taskRepository.save(task);
    }
    public void deleteTask(UUID taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task do not found"));

        taskRepository.deleteById(task.getId());
    }

}
