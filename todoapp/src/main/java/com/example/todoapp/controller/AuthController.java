package com.example.todoapp.controller;


import com.example.todoapp.dto.*;
import com.example.todoapp.model.User;
import com.example.todoapp.security.JwtService;
import com.example.todoapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;
    private final JwtService jwt;

    public AuthController(AuthService auth, JwtService jwt) {
        this.auth = auth;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        User u = auth.register(req);
        String token = jwt.generateToken(u.getUsername(), Map.of("roles", u.getRoles()));
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        Authentication a = auth.authenticate(req.username(), req.password());
        String token = jwt.generateToken(a.getName(), Map.of());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

