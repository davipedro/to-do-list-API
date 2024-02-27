package br.com.todolistAPI.DTOs;

import br.com.todolistAPI.domain.task.Tag;
import br.com.todolistAPI.domain.task.Priority;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskCreateDTO (@JsonAlias("title") @NotBlank String title,
                             @JsonAlias("description") String description,
                             @JsonAlias("conclusion_date") LocalDate conclusionDate,
                             @JsonAlias("custom_tags") List<Tag> tags,
                             @JsonAlias("priority") Priority priority){
}
