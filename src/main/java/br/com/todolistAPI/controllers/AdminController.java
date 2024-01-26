package br.com.todolistAPI.controllers;

import br.com.todolistAPI.DTOs.LoginResponseDTO;
import br.com.todolistAPI.DTOs.UserResponseDTO;
import br.com.todolistAPI.config.security.TokenService;
import br.com.todolistAPI.DTOs.AuthenticationDTO;
import br.com.todolistAPI.DTOs.RegisterDTO;
import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import br.com.todolistAPI.services.AdminService;
import br.com.todolistAPI.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final AdminService adminService;

    @Autowired
    TokenService tokenService;

    public AdminController(AuthenticationManager authenticationManager, AuthenticationService authenticationService, AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.adminService = adminService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login (@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO registerDTO){
        authenticationService.register(registerDTO, UserRole.ADMIN);
        return ResponseEntity.ok().body("User registered successfully");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<UserResponseDTO> userList = adminService.getAllUsers();
        return ResponseEntity.ok().body(userList);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<UserResponseDTO>> getAllAdmins(){
        List<UserResponseDTO> adminList = adminService.getAllAdmins();
        return ResponseEntity.ok().body(adminList);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id){
        UserResponseDTO user = adminService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }
}
