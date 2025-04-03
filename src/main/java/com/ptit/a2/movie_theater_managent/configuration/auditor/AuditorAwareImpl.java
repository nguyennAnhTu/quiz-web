package com.ptit.a2.movie_theater_managent.configuration.auditor;


import com.ptit.a2.movie_theater_managent.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuditorConstant.ANONYMOUS;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuditorConstant.SYSTEM;


@Slf4j
public class AuditorAwareImpl implements AuditorAware<Integer> {
  private static final int DEFAULT_AUDITOR = -1;


  @Override
  public Optional<Integer> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Authentication: " + authentication);

    if (authentication == null || !authentication.isAuthenticated() ||
          authentication instanceof AnonymousAuthenticationToken) {
      return Optional.of(DEFAULT_AUDITOR);
    }

    Object principal = authentication.getPrincipal();

    if (principal instanceof Integer userId) {
      return Optional.of(userId);
    } else if (principal instanceof String) {
      try {
        return Optional.of(Integer.parseInt((String) principal));
      } catch (NumberFormatException e) {
        log.warn("Failed to parse userId from principal: {}", principal);
      }
    }

    return Optional.of(DEFAULT_AUDITOR);
  }
}
