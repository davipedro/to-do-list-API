package br.com.todolistAPI.services;

import br.com.todolistAPI.DTOs.TaskCreateDTO;
import br.com.todolistAPI.DTOs.TaskUpdateDTO;
import br.com.todolistAPI.domain.task.Tag;
import br.com.todolistAPI.domain.task.Priority;
import br.com.todolistAPI.domain.task.Task;
import br.com.todolistAPI.exceptions.task.TaskNotFoundException;
import br.com.todolistAPI.repositories.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    void createTask_ShouldCreateTask_WhenPassedDtoValuesAreCorrect() {
        List<Tag> lista = new ArrayList<>();
        LocalDate conclusionDate = LocalDate.of(2024,1,3);
        TaskCreateDTO taskToSave = new TaskCreateDTO("title","description",conclusionDate, lista, Priority.HIGH);

        Task task = new Task(taskToSave.title(), taskToSave.description(), taskToSave.conclusionDate(), taskToSave.tags(), taskToSave.priority());

        Assertions.assertEquals(taskToSave.title(), task.getTitle());
        Assertions.assertEquals(taskToSave.description(), task.getDescription());
        Assertions.assertEquals(taskToSave.conclusionDate(), task.getConclusionDate());
    }

    @Test
    void createTask_ShouldSaveTask_WhenPassedDtoValuesAreCorrect(){
        TaskCreateDTO taskToSave = mock(TaskCreateDTO.class);

        taskService.createTask(taskToSave);

        verify(taskRepository).save(any(Task.class));
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
        TaskUpdateDTO taskViewDTO = mock(TaskUpdateDTO.class);
        when(taskRepository.findById(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(TaskNotFoundException.class,
                () -> {
                    taskService.putTask(uuid, taskViewDTO);
                });
    }

    @Test
    void putTask_ShouldUpdateTask_WhenTaskDtoFieldsAreNotEmpty(){
        Task task = new Task();
        LocalDate localDate = mock(LocalDate.class);
        TaskUpdateDTO taskViewDTO = new TaskUpdateDTO("title","description",localDate, false, Collections.emptyList() ,Priority.HIGH);

        if (!(taskViewDTO.title().isEmpty())) task.setTitle(taskViewDTO.title());
        if (!(taskViewDTO.description().isEmpty())) task.setDescription(taskViewDTO.description());
        task.setConclusionDate(taskViewDTO.conclusionDate());
        task.setLastUpdate(LocalDate.now());

        Assertions.assertEquals(taskViewDTO.title(), task.getTitle());
        Assertions.assertEquals(taskViewDTO.description(), task.getDescription());
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
    @Test
    void deleteTask_ShouldCallTaskRepositoryDeleteById_WhenTheTaskWasFind(){
        UUID uuid = UUID.randomUUID();
        Task task = mock(Task.class);
        task.setId(uuid);
        when(taskRepository.findById(uuid)).thenReturn(Optional.of(task));

        taskService.deleteTask(uuid);

        verify(taskRepository, times(1)).deleteById(task.getId());
    }
}