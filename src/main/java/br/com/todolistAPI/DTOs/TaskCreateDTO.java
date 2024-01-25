package br.com.todolistAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskCreateDTO (@JsonAlias("title") @NotBlank String title,
                            @JsonAlias("description") String description,
                            @JsonAlias("conclusion_date") LocalDate conclusionDate){
}
