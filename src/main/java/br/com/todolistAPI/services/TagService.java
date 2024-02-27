package br.com.todolistAPI.services;

import br.com.todolistAPI.DTOs.TagCreateDTO;
import br.com.todolistAPI.DTOs.TagCreateResponseDTO;
import br.com.todolistAPI.DTOs.TagResponseDTO;
import br.com.todolistAPI.DTOs.TagUpdateDTO;
import br.com.todolistAPI.domain.task.Tag;
import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.exceptions.tag.TagCouldNotBeCreated;
import br.com.todolistAPI.exceptions.tag.TagNameAlreadyExistsException;
import br.com.todolistAPI.exceptions.tag.TagNotFoundException;
import br.com.todolistAPI.exceptions.user.UserNotFoundException;
import br.com.todolistAPI.repositories.TagRepository;
import br.com.todolistAPI.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public TagService(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    public TagCreateResponseDTO createTag(String userId, TagCreateDTO tagCreateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        boolean existsTag = tagRepository.findUserTagByName(userId,tagCreateDTO.tagName());
        if (existsTag) throw new TagNameAlreadyExistsException();

        Tag tag = new Tag(user, tagCreateDTO.tagName(), tagCreateDTO.tagColor());
        try {
            tagRepository.save(tag);
        } catch (RuntimeException e){
            throw new TagCouldNotBeCreated();
        }
        return new TagCreateResponseDTO(tag.getId());
    }

    public List<TagResponseDTO> getAllUserTags(String userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Tag> tags = tagRepository.getAllUserCustomTags(userId)
                .orElseThrow(TagNotFoundException::new);

        return tags.stream()
                .map(tag -> new TagResponseDTO
                (
                    tag.getId(),
                    tag.getName(),
                    tag.getTagColor()
                )
                ).toList();
    }

    public TagResponseDTO getUserTagById(UUID tagId, String userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);

        Tag tag = tagRepository.getUserCustomTag(tagId, userId)
                .orElseThrow(TagNotFoundException::new);

        return new TagResponseDTO(tag.getId(), tag.getName(), tag.getTagColor());
    }

    public TagResponseDTO putTag(UUID tagId, TagUpdateDTO tagUpdateDTO){
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);

        if (tagUpdateDTO.tagName() != null) tag.setName(tagUpdateDTO.tagName());
        if (tagUpdateDTO.tagColor() != null) tag.setTagColor(tagUpdateDTO.tagColor());
        tagRepository.save(tag);

        return new TagResponseDTO(
                tag.getId(),
                tag.getName(),
                tag.getTagColor()
        );
    }

    public void deleteTag(UUID tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);

        tagRepository.deleteById(tag.getId());
    }
}
