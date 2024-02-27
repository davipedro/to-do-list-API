package br.com.todolistAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagCreateDTO (@JsonAlias("tag_name") @NotBlank String tagName,
                            @JsonAlias("tag_color") @NotBlank String tagColor,
                            @JsonAlias("user_token") @NotBlank String userToken){
}
