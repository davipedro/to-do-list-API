package br.com.todolistAPI.task;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskDTO (@JsonAlias("id") Long id,
                       @JsonAlias("title") @NotBlank String title,
                       @JsonAlias("description") @NotBlank String description,
                       @JsonAlias("conclusion_date") LocalDate conclusionDate){

}
