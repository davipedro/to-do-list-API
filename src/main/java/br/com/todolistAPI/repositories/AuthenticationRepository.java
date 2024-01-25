package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationRepository extends JpaRepository<User,Long> {
    UserDetails findByUsername(String login);
}
