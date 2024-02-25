package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<User, String> {

    Optional<Page<User>> findByRole(UserRole role, Pageable pageable);
}
