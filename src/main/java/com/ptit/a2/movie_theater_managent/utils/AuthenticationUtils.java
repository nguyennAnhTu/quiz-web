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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //log.info(authentication.toString());

    if (authentication == null || !authentication.isAuthenticated()) {
      return null; // Không có authentication hoặc chưa đăng nhập
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof String principalStr) {
      if ("anonymousUser".equals(principalStr)) {
        return null; // Anonymous user
      }
      try {
        log.info(principalStr);
        return Integer.parseInt(principalStr); // Parse userId từ String
      } catch (NumberFormatException e) {
        throw new IllegalStateException("Invalid userId format in authentication principal: " + principalStr, e);
      }
    } else if (principal instanceof Integer) {
      return (Integer) principal; // Đã là Integer
    }
    throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
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
