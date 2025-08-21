package com.example.chatapp.dto;


import lombok.*;
import java.time.Instant;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatMessage {
    public enum Type { CHAT, JOIN, LEAVE }

    private Type type;
    private String roomId;
    private String sender;
    private String content;     // only used for CHAT
    private Instant timestamp;  // set by server
}