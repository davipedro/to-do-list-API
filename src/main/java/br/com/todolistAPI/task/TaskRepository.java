package br.com.todolistAPI.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitleContainingIgnoreCase(String title);
    Optional<Task> findByCreationDate(LocalDate creationDate);
}
