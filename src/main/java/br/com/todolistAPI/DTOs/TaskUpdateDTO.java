package br.com.todolistAPI.DTOs;

import br.com.todolistAPI.domain.task.Priority;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskUpdateDTO (@JsonAlias("title") String title,
                             @JsonAlias("description") String description,
                             @JsonAlias("conclusion_date") LocalDate conclusionDate,
                             @JsonAlias("completed") Boolean completed,
                             @JsonAlias("custom_tags") List<TagUpdateDTO> tags,
                             @JsonAlias("priority") Priority priority){
}
