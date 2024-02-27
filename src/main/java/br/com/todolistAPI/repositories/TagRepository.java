package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.task.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("SELECT t FROM customTag t WHERE t.id = :tagId AND t.user.id = :userId")
    Optional<Tag> getUserCustomTag(@Param("tagId") UUID tagId, @Param("userId") String userId);

    @Query("SELECT t FROM customTag t WHERE t.user.id = :userId")
    Optional<List<Tag>> getAllUserCustomTags(@Param("userId") String userId);

    @Query("SELECT COUNT(t) > 0 FROM customTag t WHERE t.user.id = :userId AND t.tagName = :tagName")
    boolean findUserTagByName(@Param("userId") String userId, @Param("tagName") String tagName);
}