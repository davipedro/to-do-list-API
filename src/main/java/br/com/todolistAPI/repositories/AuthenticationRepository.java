package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<User,String> {
    UserDetails findByUsername(String login);
    Optional<List<User>> findByRole(UserRole role);
}
