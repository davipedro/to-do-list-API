package br.com.todolistAPI.task;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "task")
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private UUID taskId;
    @Column(unique = true)
    private String title;
    private String description;
    private LocalDate creationDate;
    private LocalDate conclusionDate;
    private LocalDate lastUpdate;

    public Task(TaskDTO taskDTO){
        this.taskId = UUID.randomUUID();
        this.title = taskDTO.getTitle();
        this.description = taskDTO.getDescription();
        this.creationDate = LocalDate.now();
        this.conclusionDate = taskDTO.getConclusionDate();
        this.lastUpdate = LocalDate.now();
    }

    public Task(){}

    public UUID getTaskId() {
        return taskId;
    }

    public Long getId() {
        return id;
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

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
