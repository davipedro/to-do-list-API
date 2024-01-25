package br.com.todolistAPI.services;

import br.com.todolistAPI.domain.user.RegisterDTO;
import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import br.com.todolistAPI.exceptions.user.AlreadyRegisteredUserException;
import br.com.todolistAPI.exceptions.user.RoleNotFoundException;
import br.com.todolistAPI.repositories.AuthenticationRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final AuthenticationRepository repository;

    public AuthenticationService(AuthenticationRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }

    public void register(RegisterDTO data, UserRole role){
        if (repository.findByUsername(data.login()) != null) throw new AlreadyRegisteredUserException("User already registered");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        if (!UserRole.verifyRole(role)) throw new RoleNotFoundException("Role not found");

        User newUser = new User(data.login(), encryptedPassword, role);
        repository.save(newUser);
    }
}
