package br.com.todolistAPI.task;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "task")
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate conclusionDate;

    public Task(String title, String description, LocalDate conclusionDate) {
        this.title = title;
        this.description = description;
        this.creationDate = LocalDate.now();
        this.conclusionDate = conclusionDate;
    }

    public Task(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {
        this.title = titulo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descricao) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(LocalDate dataConclusao) {
        this.conclusionDate = dataConclusao;
    }
}
