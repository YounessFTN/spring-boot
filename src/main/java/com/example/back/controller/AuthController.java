package com.example.back.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.entity.User;
import com.example.back.repository.UserRepository;
import com.example.back.security.JwtUtils;
import com.example.back.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    record RegisterRequest(String name, String email, String phone, String password) {}
    record LoginRequest(String email, String password) {}
    record AuthResponse(String token, String name, String email) {}

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserService userService,
                          UserRepository userRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.email()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email déjà utilisé"));
        }

        User user = userService.createUser(
                new User(req.name(), req.email(), req.phone(), req.password()));

        String token = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, user.getName(), user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email ou mot de passe incorrect"));
        }

        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        String token = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, user.getName(), user.getEmail()));
    }
}
