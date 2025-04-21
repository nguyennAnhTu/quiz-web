package com.ptit.a2.movie_theater_managent.service.websocket;

import java.util.Collection;

public interface WebSocketService {
    void sendMessageToUser(String username, Object message);

    void sendMessageToUsers(Collection<String> usernames, Object message);

    void sendMessage(String topic, Object message);
  }


