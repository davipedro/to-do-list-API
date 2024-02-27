package br.com.todolistAPI.controllers;

import br.com.todolistAPI.DTOs.TagCreateDTO;
import br.com.todolistAPI.DTOs.TagCreateResponseDTO;
import br.com.todolistAPI.DTOs.TagResponseDTO;
import br.com.todolistAPI.DTOs.TagUpdateDTO;
import br.com.todolistAPI.config.security.TokenService;
import br.com.todolistAPI.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag Controller", description = "End points para gerenciamento de tags")
public class TagController {

    private final TagService tagService;
    private final TokenService tokenService;

    public TagController(TagService tagService, TokenService tokenService) {
        this.tagService = tagService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Cria uma nova tag" , security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping
    public ResponseEntity<Object> createTag(@RequestBody @Valid TagCreateDTO tagCreateDTO) {
        String userId = tokenService.validateToken(tagCreateDTO.userToken());

        TagCreateResponseDTO tagResponse = tagService.createTag(userId, tagCreateDTO);
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    @Operation(summary = "Obtém todas as tags do usuário" , security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/{userToken}")
    public ResponseEntity<List<TagResponseDTO>> getAllTags(@PathVariable String userToken){
        String userId = tokenService.validateToken(userToken);

        List<TagResponseDTO> userTags = tagService.getAllUserTags(userId);
        return ResponseEntity.ok(userTags);
    }

    @Operation(summary = "Obtém uma tag do usuário pelo id" , security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/{userToken}/{tagId}")
    public ResponseEntity<TagResponseDTO> getAllTags(@PathVariable UUID tagId, @PathVariable String userToken){
        String userId = tokenService.validateToken(userToken);

        TagResponseDTO userTag = tagService.getUserTagById(tagId,userId);
        return ResponseEntity.ok(userTag);
    }

    @Operation(summary = "Atualiza uma tag", security = { @SecurityRequirement(name = "bearer-key") })
    @PutMapping("/{tagId}")
    public ResponseEntity<TagResponseDTO> putTask(@PathVariable UUID tagId, @RequestBody @Valid TagUpdateDTO tagUpdateDTO) {
        TagResponseDTO tagResponse = tagService.putTag(tagId, tagUpdateDTO);
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    @Operation(summary = "Deleta uma tag", security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("/{tagId}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID tagId) {
        tagService.deleteTag(tagId);
        return ResponseEntity.ok("Deleted successfully");
    }
}
