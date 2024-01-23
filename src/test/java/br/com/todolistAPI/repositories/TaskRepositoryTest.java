package br.com.todolistAPI.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //tras todos os metodos default do JpaRepository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //por padrao ele coloca um banco de dados em memoria para teste
@ActiveProfiles("test") //para definir o banco de dados por meio do nome do arquivo properties
class TaskRepositoryTest {

    @Test
    void findByTitleContainingIgnoreCase() {
    }

    @Test
    void findByCreationDate() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteById() {
    }
}