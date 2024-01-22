package br.com.todolistAPI.domain.task;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskDTO (@JsonAlias("id") UUID taskId,
                       @JsonAlias("title") @NotBlank String title,
                       @JsonAlias("description") @NotBlank String description,
                       @JsonAlias("conclusion_date") LocalDate conclusionDate){
}
