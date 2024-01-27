package br.com.todolistAPI.services;


import br.com.todolistAPI.DTOs.UserResponseDTO;
import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import br.com.todolistAPI.exceptions.user.UserNotFoundException;
import br.com.todolistAPI.exceptions.admin.UserDeletionException;
import br.com.todolistAPI.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<UserResponseDTO> getAllUsers(){
        List<User> user = adminRepository.findByRole(UserRole.USER)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.stream().map(u -> new UserResponseDTO(
                u.getId(),
                u.getUsername(),
                u.getRole()
        )).collect(Collectors.toList());
    }

    public List<UserResponseDTO> getAllAdmins(){
        List<User> admin = adminRepository.findByRole(UserRole.ADMIN)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return admin.stream().map(adm -> new UserResponseDTO(
                adm.getId(),
                adm.getUsername(),
                adm.getRole()
        )).collect(Collectors.toList());
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