package com.example.chatapp.service;


import com.example.chatapp.dto.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class ChatService {
    private static final int MAX_HISTORY = 100;
    private final Map<String, Deque<ChatMessage>> history = new ConcurrentHashMap<>();

    public void save(String roomId, ChatMessage msg) {
        history.computeIfAbsent(roomId, k -> new ArrayDeque<>()).addLast(msg);
        if (history.get(roomId).size() > MAX_HISTORY) {
            history.get(roomId).removeFirst();
        }
    }

    public List<ChatMessage> recent(String roomId) {
        return Optional.ofNullable(history.get(roomId))
                .map(ArrayDeque::new)
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
    }
}
