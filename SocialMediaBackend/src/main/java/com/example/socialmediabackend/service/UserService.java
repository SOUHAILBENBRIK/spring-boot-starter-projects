package com.example.socialmediabackend.service;

import com.example.socialmediabackend.domain.User;
import com.example.socialmediabackend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserService {
    private final UserRepository users;

    public User get(Long id) { return users.findById(id).orElseThrow(); }

    public User create(String username, String name) {
        return users.save(User.builder().username(username).name(name).build());
    }
}
