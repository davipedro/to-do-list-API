package br.com.todolistAPI.domain.task;

import br.com.todolistAPI.domain.user.User;
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
    private Boolean completed = Boolean.FALSE;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(TaskViewDTO taskViewDTO, LocalDate currentDate){
        this.title = taskViewDTO.title();
        this.description = taskViewDTO.description();
        this.creationDate = currentDate;
        this.conclusionDate = taskViewDTO.conclusionDate();
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

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
