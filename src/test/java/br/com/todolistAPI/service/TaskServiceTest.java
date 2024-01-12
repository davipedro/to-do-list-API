package br.com.todolistAPI.service;

import br.com.todolistAPI.task.Task;
import br.com.todolistAPI.task.TaskDTO;
import br.com.todolistAPI.task.TaskRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Captor
    private ArgumentCaptor<Task> taskCaptor;

    @Test
    void shouldReturnAllTasks() {
        Task task1 = mock(Task.class);
        UUID taskId1 = UUID.fromString("11118111-1111-1111-1111-111711111321");
        when(task1.getId()).thenReturn(taskId1);
        when(task1.getTitle()).thenReturn("task1 Title");
        when(task1.getDescription()).thenReturn("A description");
        when(task1.getCreationDate()).thenReturn(LocalDate.of(2024,1,11));
        when(task1.getConclusionDate()).thenReturn(LocalDate.of(2024,1,31));
        when(task1.getLastUpdate()).thenReturn(LocalDate.of(2024,1,11));

        Task task2 = mock(Task.class);
        UUID taskId2 = UUID.fromString("11118111-1111-1111-1111-121711111321");
        when(task2.getId()).thenReturn(taskId2);
        when(task1.getTitle()).thenReturn("task2 Title");
        when(task2.getDescription()).thenReturn("Other description");
        when(task2.getCreationDate()).thenReturn(LocalDate.of(2024,1,12));
        when(task2.getConclusionDate()).thenReturn(LocalDate.of(2024,2,1));
        when(task2.getLastUpdate()).thenReturn(LocalDate.of(2024,1,11));

        List<Task> mockTasks = new ArrayList<>(Arrays.asList(task1, task2));

        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(mockTasks, result);
    }

    @Test
    void shouldFailToReturnAllTasksByRepositoryReturnsNull() {
        when(taskRepository.findAll()).thenReturn(null);

        List<Task> result = taskService.getAllTasks();

        assertNull(result);
    }

    @Test
    void shouldReturnTasksByTitle() {
        String title = "A Title";
        Task task1 = mock(Task.class);
        UUID taskId1 = UUID.fromString("11118111-1111-1111-1111-111711111321");
        when(task1.getId()).thenReturn(taskId1);
        when(task1.getTitle()).thenReturn("task1 Title");
        when(task1.getDescription()).thenReturn("A description");
        when(task1.getCreationDate()).thenReturn(LocalDate.of(2024,1,11));
        when(task1.getConclusionDate()).thenReturn(LocalDate.of(2024,1,31));
        when(task1.getLastUpdate()).thenReturn(LocalDate.of(2024,1,11));

        Task task2 = mock(Task.class);
        UUID taskId2 = UUID.fromString("11111111-1111-1111-1111-111111111321");
        when(task2.getId()).thenReturn(taskId2);
        when(task2.getTitle()).thenReturn("task2 Title");
        when(task2.getDescription()).thenReturn("Other description");
        when(task2.getCreationDate()).thenReturn(LocalDate.of(2024,1,12));
        when(task2.getConclusionDate()).thenReturn(LocalDate.of(2024,2,1));
        when(task2.getLastUpdate()).thenReturn(LocalDate.of(2024,1,11));

        Task task3 = mock(Task.class);
        UUID taskId3 = UUID.fromString("51116111-1111-1111-1111-111111111321");
        when(task3.getId()).thenReturn(taskId3);
        when(task3.getTitle()).thenReturn("task3 do not should appear");
        when(task3.getDescription()).thenReturn("description do not should appear");
        when(task3.getCreationDate()).thenReturn(LocalDate.of(2024,1,12));
        when(task3.getConclusionDate()).thenReturn(LocalDate.of(2024,2,1));
        when(task3.getLastUpdate()).thenReturn(LocalDate.of(2024,1,11));

        Optional<List<Task>> mockTasks = Optional.of(Arrays.asList(task1, task2));

        when(taskRepository.findByTitleContainingIgnoreCase(title)).thenReturn(mockTasks);

        Optional<List<Task>> result = taskService.getByTitle(title);

        Assertions.assertEquals(mockTasks, result);
    }

    @Test
    void getByCreationDate() {
        LocalDate creationDate = LocalDate.of(2024,1,11);

        Task task1 = mock(Task.class);
        UUID taskId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(task1.getId()).thenReturn(taskId);
        when(task1.getTitle()).thenReturn("task1 Title");
        when(task1.getDescription()).thenReturn("A description");
        when(task1.getCreationDate()).thenReturn(LocalDate.of(2024,1,11));
        when(task1.getConclusionDate()).thenReturn(LocalDate.of(2024,1,31));
        when(task1.getLastUpdate()).thenReturn(LocalDate.of(2024,1,11));

        Optional<List<Task>> taskOptional = Optional.of(List.of(task1));

        when(taskRepository.findByCreationDate(creationDate)).thenReturn(taskOptional);

        Optional<List<Task>> result = taskService.getByCreationDate(creationDate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(taskOptional, result);
    }

    @Test
    void ShouldSaveTaskByCallingCreateTaskPassingDTO() {
        TaskDTO taskDTO = mock(TaskDTO.class);
        UUID taskId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        when(taskDTO.taskId()).thenReturn(taskId);
        when(taskDTO.title()).thenReturn("task1 Title");
        when(taskDTO.description()).thenReturn("A description");
        when(taskDTO.conclusionDate()).thenReturn(LocalDate.of(2024,1,31));

        taskService.createTask(taskDTO);

        verify(taskRepository).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();
        Assertions.assertEquals(taskDTO.title(), savedTask.getTitle());
        Assertions.assertEquals(taskDTO.description(), savedTask.getDescription());
        Assertions.assertEquals(taskDTO.conclusionDate(), savedTask.getConclusionDate());
    }

    @Test
    void shouldUpdateTaskSearchingByID() {
        LocalDate conclusionDate = LocalDate.of(2014,1,12);

        Task taskBeforeSave = new Task("title","description",LocalDate.now(), conclusionDate);
        UUID taskId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        TaskDTO taskDTO = new TaskDTO(taskId,"New Title", "New Description", LocalDate.now().plusDays(1));
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskBeforeSave));

        taskService.putTask(taskId,taskDTO);
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        Assertions.assertNotNull(taskOptional);

        verify(taskRepository).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();
        Assertions.assertEquals(taskBeforeSave.getTitle(), savedTask.getTitle());
        Assertions.assertEquals(taskBeforeSave.getDescription(), savedTask.getDescription());
        Assertions.assertEquals(taskBeforeSave.getConclusionDate(), savedTask.getConclusionDate());
    }

    @Test
    void shouldDeleteTaskSearchingByID() {
        UUID taskId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        LocalDate conclusionDate = LocalDate.of(2014,1,12);

        Task taskToBeDeleted = new Task("title","description",LocalDate.now(),conclusionDate);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskToBeDeleted));

        boolean result = taskService.deleteTask(taskId);
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        assertNotNull(optionalTask);

        //assertTrue(result);
        verify(taskRepository).deleteById(optionalTask.get().getId());
    }
    @Test
    void shouldFailToDelete(){
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        boolean result = taskService.deleteTask(taskId);

        assertFalse(result);
        verify(taskRepository, Mockito.never()).deleteById((UUID) any());
    }
}