package br.com.todolistAPI.task;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "task")
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "conclusion_date")
    private LocalDate conclusionDate;
    private LocalDate lastUpdate;

    public Task(TaskDTO taskDTO, LocalDate currentDate){
        this.title = taskDTO.title();
        this.description = taskDTO.description();
        this.creationDate = currentDate;
        this.conclusionDate = taskDTO.conclusionDate();
        this.lastUpdate = LocalDate.now();
    }

    public Task(String title, String description,LocalDate currentDate ,LocalDate conclusionDate){
        this.title = title;
        this.description = description;
        this.creationDate = currentDate;
        this.conclusionDate = conclusionDate;
        this.lastUpdate = LocalDate.now();
    }

    public Task(){}

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(LocalDate conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
