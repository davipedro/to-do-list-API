package br.com.todolistAPI.controllers;

import br.com.todolistAPI.DTOs.LoginResponseDTO;
import br.com.todolistAPI.config.security.TokenService;
import br.com.todolistAPI.domain.user.AuthenticationDTO;
import br.com.todolistAPI.domain.user.RegisterDTO;
import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import br.com.todolistAPI.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    @Autowired
    TokenService tokenService;

    public UserController(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login (@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO){
        authenticationService.register(registerDTO, UserRole.USER);
        return ResponseEntity.ok().body("User registered successfully");
    }
}
