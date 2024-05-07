package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
        Optional<List<Task>> findByTitleContainingIgnoreCase(String title);
        Optional<List<Task>> findByCreationDate(LocalDate creationDate);
        Optional<Task> findById(UUID taskId);
        @Query("SELECT t FROM task t JOIN t.tags tag WHERE tag.tagName = :tagName")
        Optional<List<Task>> findByTagName(@Param("tagName") String tagName);
        void deleteById(UUID taskId);
}
