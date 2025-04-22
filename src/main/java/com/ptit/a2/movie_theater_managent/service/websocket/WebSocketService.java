package com.ptit.a2.movie_theater_managent.service.websocket;

import java.util.Collection;

public interface WebSocketService {
  void sendMessageToUser(Integer userId, Object message);

  void sendMessageToUsers(Collection<Integer> userIds, Object message);

  void sendMessage(String topic, Object message);
  }


