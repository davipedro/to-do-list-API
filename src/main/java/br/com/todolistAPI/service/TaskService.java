package br.com.todolistAPI.service;

import br.com.todolistAPI.task.Task;
import br.com.todolistAPI.task.TaskDTO;
import br.com.todolistAPI.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
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

    public void createTask(TaskDTO taskToSave){
        Task task = new Task(taskToSave);
        taskRepository.save(task);
    }

    public void patchTask(TaskDTO taskDTO){
        Optional<Task> optionalTask =  taskRepository.findByTaskId(taskDTO.getId());

        Task task = optionalTask.orElseThrow(() -> new NoSuchElementException("Task não encontrada"));

        task.setTitle(taskDTO.getTitle());
        if(taskDTO.getDescription() != null){
            task.setDescription(taskDTO.getDescription());
        }
        if(taskDTO.getConclusionDate() != null){
            task.setConclusionDate(taskDTO.getConclusionDate());
        }
        task.setLastUpdate(LocalDate.now());

        taskRepository.save(task);
    }

    public void putTask(TaskDTO taskDTO){
        Optional<Task> optionalTask =  taskRepository.findByTaskId(taskDTO.getId());

        Task task = optionalTask.orElseThrow(() -> new NoSuchElementException("Task não encontrada"));

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setConclusionDate(taskDTO.getConclusionDate());
        task.setLastUpdate(LocalDate.now());

        taskRepository.save(task);
    }

    public void deleteTask(UUID taskId){
        Optional<Task> optionalTask =  taskRepository.findByTaskId(taskId);

        Task task = optionalTask.orElseThrow(() -> new NoSuchElementException("Task não encontrada"));

        taskRepository.deleteById(task.getId());
    }

}
