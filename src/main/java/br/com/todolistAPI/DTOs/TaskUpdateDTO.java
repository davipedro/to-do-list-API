package br.com.todolistAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskUpdateDTO (@JsonAlias("title") String title,
                             @JsonAlias("description") String description,
                             @JsonAlias("conclusion_date") LocalDate conclusionDate,
                             @JsonAlias("completed") Boolean completed){
}
