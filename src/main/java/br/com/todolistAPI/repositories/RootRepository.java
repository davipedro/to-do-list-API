package br.com.todolistAPI.repositories;

import br.com.todolistAPI.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RootRepository extends JpaRepository<User, String> {
}
