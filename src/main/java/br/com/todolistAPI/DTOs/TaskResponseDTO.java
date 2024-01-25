package br.com.todolistAPI.DTOs;

import java.time.LocalDate;

public record TaskResponseDTO (String title,
                               String description,
                               LocalDate creationDate,
                               LocalDate conclusionDate,
                               LocalDate lastUpdate,
                               Boolean completed) {
}
