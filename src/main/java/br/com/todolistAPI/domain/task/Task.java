package br.com.todolistAPI.domain.task;

import br.com.todolistAPI.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;
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
    @Column(name = "last_update")
    private LocalDate lastUpdate;
    private Boolean completed = Boolean.FALSE;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Task(String title, String description, LocalDate conclusionDate, List<Tag> tags, Priority priority){
        this.title = title;
        this.description = description;
        this.creationDate = LocalDate.now();
        this.conclusionDate = conclusionDate;
        this.lastUpdate = LocalDate.now();
        this.tags = tags;
        this.priority = priority;
    }

    public Task(String title, String description, LocalDate currentDate ,LocalDate conclusionDate){
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

    public void setId(UUID id){
        this.id = id;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }
}
