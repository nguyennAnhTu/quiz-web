package com.ptit.a2.movie_theater_managent.configuration.auditor;


import com.ptit.a2.movie_theater_managent.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuditorConstant.ANONYMOUS;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuditorConstant.SYSTEM;


@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {


    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Authentication: " + authentication);

    if (Objects.nonNull(authentication) && !this.isAnonymous() && (Objects.nonNull(authentication.getPrincipal()))) {
      return Optional.of((authentication.getPrincipal().toString()));


    }
    return Optional.of(SYSTEM);
  }


  private boolean isAnonymous() {
    return SecurityContextHolder.getContext().getAuthentication().getName().equals(ANONYMOUS);
  }
}
