package br.com.todolistAPI.DTOs;

import java.util.UUID;

public record TagResponseDTO(UUID id,
                             String tagName,
                             String tagColor){
}
