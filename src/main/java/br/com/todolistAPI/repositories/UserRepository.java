package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
