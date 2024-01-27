package br.com.todolistAPI.controllers;

import br.com.todolistAPI.DTOs.AuthenticationDTO;
import br.com.todolistAPI.DTOs.LoginResponseDTO;
import br.com.todolistAPI.DTOs.RegisterDTO;
import br.com.todolistAPI.config.security.TokenService;
import br.com.todolistAPI.domain.user.User;
import br.com.todolistAPI.domain.user.UserRole;
import br.com.todolistAPI.services.AuthenticationService;
import br.com.todolistAPI.services.RootService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/root-admin")
@Tag(name = "Root-admin Controller", description = "End points para o admin root, é único no sistema e possui todas as permissões")
public class RootController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    private final RootService rootService;

    @Autowired
    TokenService tokenService;

    public RootController(AuthenticationManager authenticationManager, AuthenticationService authenticationService, RootService rootService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.rootService = rootService;
    }

    @Operation(summary = "Permite o login do admin root")
    @PostMapping("/auth/login")
    public ResponseEntity<Object> login (@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @Operation(summary = "Permite criar o usuário ROOT (só pode haver um)")
    @PostMapping("/auth/register")
    public ResponseEntity<String> registerRoot(@RequestBody @Valid RegisterDTO registerDTO){
        authenticationService.registerRoot(registerDTO, UserRole.ROOT);
        return ResponseEntity.ok().body("The root was successfully registered");
    }

    @Operation(summary = "Permite o cadastro de usuários administradores")
    @PostMapping("/auth/admin-register")
    public ResponseEntity<String> registerAdmin(@RequestBody @Valid RegisterDTO registerDTO){
        authenticationService.register(registerDTO, UserRole.ADMIN);
        return ResponseEntity.ok().body("User registered successfully");
    }

    @Operation(summary = "Permite excluir um administrador pelo id")
    @DeleteMapping("/admin/{adminId}")
    public ResponseEntity<String> deleteAdminById(@PathVariable("adminId") String adminId){
        rootService.deleteAdminById(adminId);
        return ResponseEntity.ok().body("Admin deleted successfully");
    }
}
