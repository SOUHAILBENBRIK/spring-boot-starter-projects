package com.example.chatapp.controller;


import com.example.chatapp.dto.ChatMessage;
import com.example.chatapp.dto.PresenceUpdate;
import com.example.chatapp.service.ChatService;
import com.example.chatapp.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final PresenceService presenceService;

    // Client sends to: /app/rooms/{roomId}/send
    @MessageMapping("/rooms/{roomId}/send")
    public void handleSend(@DestinationVariable String roomId, @Payload ChatMessage incoming) {
        ChatMessage msg = ChatMessage.builder()
                .type(ChatMessage.Type.CHAT)
                .roomId(roomId)
                .sender(incoming.getSender())
                .content(incoming.getContent())
                .timestamp(Instant.now())
                .build();

        chatService.save(roomId, msg);
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId, msg);
    }

    // Client sends to: /app/rooms/{roomId}/join
    @MessageMapping("/rooms/{roomId}/join")
    public void handleJoin(@DestinationVariable String roomId, @Payload ChatMessage joinRequest) {
        Set<String> users = presenceService.addUser(roomId, joinRequest.getSender());

        ChatMessage notice = ChatMessage.builder()
                .type(ChatMessage.Type.JOIN)
                .roomId(roomId)
                .sender(joinRequest.getSender())
                .timestamp(Instant.now())
                .build();

        // Broadcast a join notice and updated user list
        messagingTemplate.convertAndSend("/topic/rooms/" + roomId, notice);
        messagingTemplate.convertAndSend(
                "/topic/rooms/" + roomId + "/users",
                PresenceUpdate.builder().roomId(roomId).users(users).build()
        );
    }

    // Client sends to: /app/rooms/{roomId}/leave
    @MessageMapping("/rooms/{roomId}/leave")
    public void handleLeave(@DestinationVariable String roomId, @Payload ChatMessage leaveRequest) {
        Set<String> users = presenceService.removeUser(roomId, leaveRequest.getSender());

        ChatMessage notice = ChatMessage.builder()
                .type(ChatMessage.Type.LEAVE)
                .roomId(roomId)
                .sender(leaveRequest.getSender())
                .timestamp(Instant.now())
                .build();

        messagingTemplate.convertAndSend("/topic/rooms/" + roomId, notice);
        messagingTemplate.convertAndSend(
                "/topic/rooms/" + roomId + "/users",
                PresenceUpdate.builder().roomId(roomId).users(users).build()
        );
    }
}
