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

  // Quản lý các WebSocket session theo username
  private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(@NonNull WebSocketSession session) {
    String username = getUsernameFromSession(session);
    sessions.put(username, session);  // Lưu phiên của user vào map
  }

  @Override
  public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
    String username = getUsernameFromSession(session);
    sessions.remove(username);  // Xóa phiên khi người dùng đóng kết nối
  }

  @Override
  public void sendMessageToUser(String username, Object message) {
    simpMessagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
  }

  // Gửi tin nhắn đến nhiều người dùng theo danh sách username
  @Override
  public void sendMessageToUsers(Collection<String> usernames, Object message) {
    for (String username : usernames) {
//      NotificationsDto notificationsDto = (NotificationsDto) message;
//      notificationsDto.setId(notificationsDto.getMapReceiverNotificationId().get(username));
      sendMessageToUser(username, null);
    }
  }

  @Override
  @MessageMapping("/{topic}")
  public void sendMessage(String topic, Object message) {
    simpMessagingTemplate.convertAndSend(topic, message);
  }

  private String getUsernameFromSession(WebSocketSession session) {
    Principal principal = session.getPrincipal();

    if (principal != null) {
      return principal.getName();
    }

    throw new IllegalStateException("User is not authenticated or Principal is null.");
  }
}
