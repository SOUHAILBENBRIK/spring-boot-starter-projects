package com.example.todoapp.service;


import com.example.todoapp.dto.RegisterRequest;
import com.example.todoapp.model.User;
import com.example.todoapp.repository.UserRepository;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository users, PasswordEncoder encoder, AuthenticationManager authManager) {
        this.users = users;
        this.encoder = encoder;
        this.authManager = authManager;
    }

    public User register(RegisterRequest req) {
        if (users.existsByUsername(req.username())) throw new IllegalArgumentException("Username taken");
        if (users.existsByEmail(req.email())) throw new IllegalArgumentException("Email taken");
        User u = User.builder()
                .username(req.username())
                .email(req.email())
                .password(encoder.encode(req.password()))
                .roles(Set.of("ROLE_USER"))
                .build();
        return users.save(u);
    }

    public Authentication authenticate(String username, String rawPassword) {
        return authManager.authenticate(new UsernamePasswordAuthenticationToken(username, rawPassword));
    }
}

