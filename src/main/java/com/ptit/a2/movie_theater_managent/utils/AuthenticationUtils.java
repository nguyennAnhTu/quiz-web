package com.ptit.a2.movie_theater_managent.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationUtils {
  public static Integer getCurrentUserId() {
    Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();

    return (Integer) authenticate.getPrincipal();
  }

  public static List<GrantedAuthority> getDefaultAuthorities(boolean isAdmin) {
    List<GrantedAuthority> authorities = new ArrayList<>();

    if (isAdmin) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    else authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    log.info("(authorities): {}", authorities);

    return authorities;
  }
}
