package br.com.todolistAPI.services;

import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import br.com.todolistAPI.exceptions.user.UserNotFoundException;
import br.com.todolistAPI.exceptions.root.UserIsNotAnAdmin;
import br.com.todolistAPI.repositories.RootRepository;
import org.springframework.stereotype.Service;

@Service
public class RootService {

    private final RootRepository rootRepository;

    public RootService(RootRepository rootRepository) {
        this.rootRepository = rootRepository;
    }

    public void deleteAdminById(String adminId){
        User user = rootRepository.findById(adminId)
                .orElseThrow(() -> new UserNotFoundException("Admin not found"));

        if (user.getRole() != UserRole.ADMIN)
            throw new UserIsNotAnAdmin("User is not an admin. You can only delete admin users from this endpoint");

        rootRepository.deleteById(adminId);
    }
}
