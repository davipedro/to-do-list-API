package br.com.todolistAPI.services;

import br.com.todolistAPI.domain.task.Task;
import br.com.todolistAPI.domain.task.TaskViewDTO;
import br.com.todolistAPI.exceptions.task.TaskNotFoundException;
import br.com.todolistAPI.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void createTask(TaskViewDTO taskToSave){
        Task task = new Task(taskToSave, LocalDate.now());
        taskRepository.save(task);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getByTitle(String title){
        return taskRepository.findByTitleContainingIgnoreCase(title)
                .orElseThrow(() -> new TaskNotFoundException("Task(s) do not found"));
    }

    public List<Task> getByCreationDate(LocalDate creationDate){
        return taskRepository.findByCreationDate(creationDate)
                .orElseThrow(() -> new TaskNotFoundException("Task(s) do not found"));
    }

    public void putTask(UUID taskId, TaskViewDTO taskViewDTO) {
        Task task =  taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task do not found"));

        if (!(taskViewDTO.title().isEmpty())) task.setTitle(taskViewDTO.title());
        if (!(taskViewDTO.description().isEmpty())) task.setDescription(taskViewDTO.description());
        if (!taskViewDTO.completed().equals(task.getCompleted())) task.setCompleted(taskViewDTO.completed());
        task.setConclusionDate(taskViewDTO.conclusionDate());
        task.setLastUpdate(LocalDate.now());

        taskRepository.save(task);
    }
    public void deleteTask(UUID taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task do not found"));

        taskRepository.deleteById(task.getId());
    }

}
