package br.com.todolistAPI.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
        Optional<Task> findByTitleContainingIgnoreCase(String title);
        Optional<Task> findByCreationDate(LocalDate creationDate);
        Optional<Task> findByTaskId(UUID taskId);
}
