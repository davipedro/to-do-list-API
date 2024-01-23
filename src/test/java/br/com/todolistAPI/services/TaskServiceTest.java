package br.com.todolistAPI.services;

import br.com.todolistAPI.domain.task.Task;
import br.com.todolistAPI.domain.task.TaskDTO;
import br.com.todolistAPI.exceptions.task.TaskNotFoundException;
import br.com.todolistAPI.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_PassDtoValuesToTask() {
        UUID id = UUID.randomUUID();
        LocalDate conclusionDate = LocalDate.of(2024,1,3);
        LocalDate currentDate = LocalDate.of(2024,1,1);
        TaskDTO taskToSave = new TaskDTO(id,"title","description",conclusionDate);

        Task task = new Task(taskToSave, currentDate);

        Assertions.assertEquals(taskToSave.title(), task.getTitle());
        Assertions.assertEquals(taskToSave.description(), task.getDescription());
        Assertions.assertEquals(taskToSave.conclusionDate(), task.getConclusionDate());
    }

    @Test
    void getAllTasks_ShouldThrowException_WhenNoTasksExist(){
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> {
            taskService.getAllTasks();
        });
    }

    @Test
    void getByTitle_ShouldThrowException_WhenNoExistTasksWithProvidedTitle(){
        String title = "title";
        when(taskRepository.findByTitleContainingIgnoreCase(title)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> {
                    taskService.getByTitle("title");
                });
    }

    @Test
    void getByCreationDate_ShouldThrowException_WhenNoExistTasksWithProvidedCreationDate(){
        LocalDate creationDate = LocalDate.of(2024,1,1);
        when(taskRepository.findByCreationDate(creationDate)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> {
                    taskService.getByCreationDate(creationDate);
                });
    }

    @Test
    void  putTask_ShouldThrowException_WhenNoExistTasksWithProvidedId(){
        UUID uuid = UUID.randomUUID();
        TaskDTO taskDTO = mock(TaskDTO.class);
        when(taskRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> {
                    taskService.putTask(uuid,taskDTO);
                });
    }

    @Test
    void putTask_ShouldUpdateTask_WhenTaskDtoFieldsAreNotEmpty(){
        Task task = new Task();
        UUID uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        LocalDate localDate = mock(LocalDate.class);
        TaskDTO taskDTO = new TaskDTO(uuid,"title","description",localDate);

        if (!(taskDTO.title().isEmpty())) task.setTitle(taskDTO.title());
        if (!(taskDTO.description().isEmpty())) task.setDescription(taskDTO.description());
        task.setConclusionDate(taskDTO.conclusionDate());
        task.setLastUpdate(LocalDate.now());

        Assertions.assertEquals(taskDTO.title(), task.getTitle());
        Assertions.assertEquals(taskDTO.description(), task.getDescription());
        Assertions.assertNotNull(task.getConclusionDate());
        Assertions.assertNotNull(task.getLastUpdate());
    }

    @Test
    void deleteTask_ShouldThrowException_WhenNoExistTasksWithProvidedId(){
        UUID uuid = UUID.randomUUID();
        when(taskRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> {
                    taskService.deleteTask(uuid);
                });
    }


}