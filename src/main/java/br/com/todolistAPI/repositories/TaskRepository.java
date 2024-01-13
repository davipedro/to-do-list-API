package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
        Optional<List<Task>> findByTitleContainingIgnoreCase(String title);
        Optional<List<Task>> findByCreationDate(LocalDate creationDate);
        Optional<Task> findById(UUID taskId);
        void deleteById(UUID taskId);
}
