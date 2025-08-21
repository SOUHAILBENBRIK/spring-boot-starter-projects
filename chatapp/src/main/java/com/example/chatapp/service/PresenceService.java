package com.example.chatapp.service;


import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class PresenceService {
    private final Map<String, Set<String>> roomUsers = new ConcurrentHashMap<>();

    public Set<String> addUser(String roomId, String username) {
        roomUsers.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(username);
        return getUsers(roomId);
    }

    public Set<String> removeUser(String roomId, String username) {
        if (roomUsers.containsKey(roomId)) {
            roomUsers.get(roomId).remove(username);
            if (roomUsers.get(roomId).isEmpty()) roomUsers.remove(roomId);
        }
        return getUsers(roomId);
    }

    public Set<String> getUsers(String roomId) {
        return roomUsers.getOrDefault(roomId, Collections.emptySet());
    }
}
