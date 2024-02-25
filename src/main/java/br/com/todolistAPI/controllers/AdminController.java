package br.com.todolistAPI.controllers;

import br.com.todolistAPI.DTOs.AuthenticationDTO;
import br.com.todolistAPI.DTOs.LoginResponseDTO;
import br.com.todolistAPI.DTOs.UserResponseDTO;
import br.com.todolistAPI.config.security.TokenService;
import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@Tag(name = "Admin Controller", description = "End points para cadastro e login de usuários com permissões de administrador")
public class AdminController {

    private final AuthenticationManager authenticationManager;
    private final AdminService adminService;

    @Autowired
    TokenService tokenService;

    public AdminController(AuthenticationManager authenticationManager, AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.adminService = adminService;
    }

    @Operation(summary = "Permite o login de usuários administradores já cadastrados")
    @PostMapping("/auth/login")
    public ResponseEntity<Object> login (@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary = "Obtém todos os usuários com permissões básicas" , security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(Pageable pageable) {

        Page<UserResponseDTO> usersPage = adminService.getAllUsers(pageable);

        return new ResponseEntity<>(usersPage, HttpStatus.OK);
    }

    @Operation(summary = "Obtém todos os usuários com permissões de administrador", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/admins")
    public ResponseEntity<Page<UserResponseDTO>> getAllAdmins(Pageable pageable){
        Page<UserResponseDTO> adminList = adminService.getAllAdmins(pageable);
        return ResponseEntity.ok().body(adminList);
    }

    @Operation(summary = "Obtém um usuário pelo seu id (independe da permissão)", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id){
        UserResponseDTO user = adminService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @Operation(summary = "Permite excluir um usuário comum pelo id", security = { @SecurityRequirement(name = "bearer-key") })
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteAdminById(@PathVariable("userId") String userId){
        adminService.deleteUserById(userId);
        return ResponseEntity.ok().body("User deleted successfully");
    }
}
