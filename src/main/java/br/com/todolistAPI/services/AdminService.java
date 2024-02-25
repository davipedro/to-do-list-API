package br.com.todolistAPI.services;


import br.com.todolistAPI.DTOs.UserResponseDTO;
import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import br.com.todolistAPI.exceptions.admin.UserDeletionException;
import br.com.todolistAPI.exceptions.user.UserNotFoundException;
import br.com.todolistAPI.repositories.AdminRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pageable){
        Page<User> user = adminRepository.findByRole(UserRole.USER, pageable)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<UserResponseDTO> userDTOList = user.stream().map(u -> new UserResponseDTO(
                u.getId(),
                u.getUsername(),
                u.getRole()
        )).toList();

        return new PageImpl<>(userDTOList, pageable, user.getTotalElements());
    }

    public Page<UserResponseDTO> getAllAdmins(Pageable pageable){
        Page<User> admin = adminRepository.findByRole(UserRole.ADMIN, pageable)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<UserResponseDTO> adminDTOList = admin.stream().map(adm -> new UserResponseDTO(
                adm.getId(),
                adm.getUsername(),
                adm.getRole()
        )).toList();

        return new PageImpl<>(adminDTOList, pageable, admin.getTotalElements());
    }

    public UserResponseDTO getUserById(String id){
        User user =  adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public void deleteUserById(String userId){
        User user = adminRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getRole() != UserRole.USER)
            throw new UserDeletionException("You cannot delete users with administrator permissions from this endpoint");

        adminRepository.deleteById(userId);
    }
}