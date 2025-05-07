package com.ptit.a2.movie_theater_managent.configuration;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
  private final ChannelInterceptor channelInterceptor;


  @Override
  public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
    stompEndpointRegistry.addEndpoint("/websocket")
          //.setAllowedOriginPatterns("https://1343-58-187-92-82.ngrok-free.app/")
          //.setAllowedOrigins("https://1343-58-187-92-82.ngrok-free.app/")
          .setAllowedOrigins(
                "http://localhost:8899", // Cho phép FE local
                "https://stunning-termite-ideal.ngrok-free.app", // Domain FE ngrok
                "https://*.ngrok-free.app" // Cho phép tất cả domain ngrok
          )
          .withSockJS().setWebSocketEnabled(true).setSessionCookieNeeded(false);
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration webSocketTransportRegistration) {
    webSocketTransportRegistration.setMessageSizeLimit(128 * 1024);
    webSocketTransportRegistration.setSendBufferSizeLimit(512 * 1024);
    webSocketTransportRegistration.setSendTimeLimit(20000);
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration channelRegistration) {
    channelRegistration.taskExecutor().corePoolSize(32).maxPoolSize(64).queueCapacity(1000);
    channelRegistration.interceptors(channelInterceptor);
  }

  @Override
  public void configureClientOutboundChannel(ChannelRegistration channelRegistration) {
    channelRegistration.taskExecutor().corePoolSize(16).maxPoolSize(32).queueCapacity(500);
  }

  @Override
  public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> list) {

  }

  @Override
  public void addReturnValueHandlers(@NonNull List<HandlerMethodReturnValueHandler> list) {

  }

  @Override
  public boolean configureMessageConverters(@NonNull List<MessageConverter> list) {
    return false;
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/topic", "/queue", "/user");
    registry.setApplicationDestinationPrefixes("/app");
    registry.setUserDestinationPrefix("/user");
  }
}