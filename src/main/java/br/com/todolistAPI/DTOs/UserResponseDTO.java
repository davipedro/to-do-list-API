package br.com.todolistAPI.DTOs;

import br.com.todolistAPI.domain.user.UserRole;

public record UserResponseDTO (String id,
                               String login,
                               UserRole userRole){
}
