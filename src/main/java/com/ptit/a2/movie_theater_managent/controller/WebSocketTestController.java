package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.service.websocket.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/ws")
@RequiredArgsConstructor
public class WebSocketTestController {
    private final WebSocketService webSocketService;

    @PostMapping("/send-to-user")
    public ResponseGeneral<Void> sendToUser(@RequestBody SendToUserRequest request) {
        webSocketService.sendMessageToUser(request.userId, request.message);
        return ResponseGeneral.ofSuccess("Message sent successfully");
    }

    @PostMapping("/send-to-users")
    public ResponseGeneral<Void> sendToUsers(@RequestBody SendToUsersRequest request) {
        webSocketService.sendMessageToUsers(request.userIds, request.message);
        return ResponseGeneral.ofSuccess("Messages sent successfully");
    }

    @PostMapping("/send-to-topic")
    public ResponseGeneral<Void> sendToTopic(@RequestBody SendToTopicRequest request) {
        webSocketService.sendMessage(request.topic, request.message);
        return ResponseGeneral.ofSuccess("Message sent to topic successfully");
    }

    // Request DTOs
    public static class SendToUserRequest {
        public Integer userId;
        public Object message;
    }

    public static class SendToUsersRequest {
        public List<Integer> userIds;
        public Object message;
    }

    public static class SendToTopicRequest {
        public String topic;
        public Object message;
    }
} 