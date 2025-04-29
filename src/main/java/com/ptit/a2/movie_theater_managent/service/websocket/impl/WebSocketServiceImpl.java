package com.ptit.a2.movie_theater_managent.service.websocket.impl;

import com.ptit.a2.movie_theater_managent.service.websocket.WebSocketService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl extends TextWebSocketHandler implements WebSocketService {
  private final SimpMessagingTemplate simpMessagingTemplate;

  // Quản lý các WebSocket session theo userId
  private final Map<Integer, WebSocketSession> sessions = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(@NonNull WebSocketSession session) {
    Integer userId = getUserIdFromSession(session);
    sessions.put(userId, session); // Lưu phiên của user vào map
  }

  @Override
  public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
    Integer userId = getUserIdFromSession(session);
    sessions.remove(userId); // Xóa phiên khi người dùng đóng kết nối
  }

  @Override
  public void sendMessageToUser(Integer userId, Object message) {
    simpMessagingTemplate.convertAndSendToUser(userId.toString(), "/queue/messages", message);
  }

  @Override
  public void sendMessageToUsers(Collection<Integer> userIds, Object message) {
    for (Integer userId : userIds) {
      sendMessageToUser(userId, message);
    }
  }

  @Override
  @MessageMapping("/{topic}")
  public void sendMessage(String topic, Object message) {
    simpMessagingTemplate.convertAndSend("/topic/" + topic, message);
  }

  private Integer getUserIdFromSession(WebSocketSession session) {
    Principal principal = session.getPrincipal();
    if (principal != null) {
      try {
        return Integer.parseInt(principal.getName()); // Giả định principal.getName() chứa userId dạng String
      } catch (NumberFormatException e) {
        throw new IllegalStateException("Invalid user ID format in Principal: " + principal.getName());
      }
    }
    throw new IllegalStateException("User is not authenticated or Principal is null.");
  }
}