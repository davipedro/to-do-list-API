package br.com.todolistAPI.domain.task;

import br.com.todolistAPI.domain.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "customTag")
@Table(name = "custom_tag")
public class Tag {

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String tagName;

    private String tagColor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Tag() {
    }

    public Tag(User user, String tagName, String tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return tagName;
    }

    public void setName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
