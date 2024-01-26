package br.com.todolistAPI.services;

import br.com.todolistAPI.DTOs.TaskCreateDTO;
import br.com.todolistAPI.DTOs.TaskUpdateDTO;
import br.com.todolistAPI.domain.task.Task;
import br.com.todolistAPI.exceptions.task.TaskCouldNotBeCreated;
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

    public void createTask(TaskCreateDTO taskToSave){
        Task task = new Task(taskToSave);
        try {
            taskRepository.save(task);
        } catch (RuntimeException e){
            throw new TaskCouldNotBeCreated("Task could not be created", e);
        }
    }

    public List<Task> getAllTasks(){
        List<Task> taskList =  taskRepository.findAll();
        if (taskList.isEmpty()) throw new TaskNotFoundException("Task(s) do not found");
        return taskList;
    }

    public List<Task> getByTitle(String title){
        return taskRepository.findByTitleContainingIgnoreCase(title)
                .orElseThrow(() -> new TaskNotFoundException("Task(s) do not found"));
    }

    public List<Task> getByCreationDate(LocalDate creationDate){
        return taskRepository.findByCreationDate(creationDate)
                .orElseThrow(() -> new TaskNotFoundException("Task(s) do not found"));
    }

    public void putTask(UUID taskId, TaskUpdateDTO taskUpdateDTO) {
        Task task =  taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task do not found"));

        if (!(taskUpdateDTO.title().isEmpty())) task.setTitle(taskUpdateDTO.title());
        if (!(taskUpdateDTO.description().isEmpty())) task.setDescription(taskUpdateDTO.description());
        if (!taskUpdateDTO.completed().equals(task.getCompleted())) task.setCompleted(taskUpdateDTO.completed());
        if (taskUpdateDTO.conclusionDate() != null) task.setConclusionDate(taskUpdateDTO.conclusionDate());
        task.setLastUpdate(LocalDate.now());

        taskRepository.save(task);
    }
    public void deleteTask(UUID taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task do not found"));

        taskRepository.deleteById(task.getId());
    }

}
