package br.com.todolistAPI.service;

import br.com.todolistAPI.task.Task;
import br.com.todolistAPI.task.TaskDTO;
import br.com.todolistAPI.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public List<Task> getAllTasks(){
       return taskRepository.findAll();
    }

    public Optional<List<Task>> getByTitle(String title){
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }

    public Optional<List<Task>> getByCreationDate(LocalDate creationDate){
        return taskRepository.findByCreationDate(creationDate);
    }

    public void createTask(TaskDTO taskToSave){
        Task task = new Task(taskToSave, LocalDate.now());
        taskRepository.save(task);
    }

    public boolean putTask(UUID taskId, TaskDTO taskDTO){
        Optional<Task> optionalTask =  taskRepository.findById(taskId);

        if (optionalTask.isEmpty()) {
            return false;
        }
        Task task = optionalTask.get();
        task.setTitle(taskDTO.title());
        task.setDescription(taskDTO.description());
        task.setConclusionDate(taskDTO.conclusionDate());
        task.setLastUpdate(LocalDate.now());

        taskRepository.save(task);
        return true;
    }

    @Transactional
    public boolean deleteTask(UUID taskId){
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()){
            return false;
        }
        taskRepository.deleteById(optionalTask.get().getId());
        return true;
    }

}
