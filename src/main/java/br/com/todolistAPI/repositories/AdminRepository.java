package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<User, String> {
    Optional<List<User>> findByRole(UserRole role);
}
