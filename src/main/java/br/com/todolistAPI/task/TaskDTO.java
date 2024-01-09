package br.com.todolistAPI.task;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskDTO(String title,
                      String description,
                      @JsonAlias("conclusion_date") LocalDate conclusionDate){
}
