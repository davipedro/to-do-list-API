package br.com.todolistAPI.DTOs;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagUpdateDTO (@JsonAlias("tag_name") String tagName,
                            @JsonAlias("tag_color") String tagColor){
}
