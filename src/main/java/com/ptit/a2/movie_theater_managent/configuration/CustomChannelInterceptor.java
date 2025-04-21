package com.ptit.a2.movie_theater_managent.configuration;

import com.ptit.a2.movie_theater_managent.constanst.enums.TokenType;
import com.ptit.a2.movie_theater_managent.entity.User;
import com.ptit.a2.movie_theater_managent.exception.authentication.UserNotFoundException;
import com.ptit.a2.movie_theater_managent.exception.base.authenticate.TokenInvalidException;
import com.ptit.a2.movie_theater_managent.service.JwtTokenService;
import com.ptit.a2.movie_theater_managent.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.AUTHORIZATION;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomChannelInterceptor implements ChannelInterceptor {

  private final JwtTokenService tokenService;
  private final UserService userService;

  @Override
  public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    log.info("Handling {}", accessor);

    assert accessor != null;
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {

      String authorizationHeader = accessor.getFirstNativeHeader(AUTHORIZATION);

      log.info("Authorization header: {}", authorizationHeader);

      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        log.error("Missing or invalid Authorization header");
        throw new TokenInvalidException();
      }

      String token = authorizationHeader.substring(7);

      Integer userId = Integer.valueOf((String) tokenService.getPrincipalFromToken(token));
      User user = userService.getById(userId);

      if (user == null) {
        log.error("User not found for userId: {}", userId);
        throw new UserNotFoundException();
      }

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            user,
            null
      );
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

      accessor.setUser(usernamePasswordAuthenticationToken);
    }

    return message;
  }
}


