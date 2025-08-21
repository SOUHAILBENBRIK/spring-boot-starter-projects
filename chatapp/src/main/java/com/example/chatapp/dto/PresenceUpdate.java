package com.example.chatapp.dto;


import lombok.*;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PresenceUpdate {
    private String roomId;
    private Set<String> users; // current users in the room
}

