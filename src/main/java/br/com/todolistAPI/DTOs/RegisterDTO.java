package br.com.todolistAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RegisterDTO (@JsonAlias("login") String login,
                           @JsonAlias("password") String password){
}
