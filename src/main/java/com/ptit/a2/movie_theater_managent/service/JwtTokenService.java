package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.constanst.enums.TokenType;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface JwtTokenService {
  String generateToken(String userId, Map<String, Object> claims, TokenType tokenType);

  Object getPrincipalFromToken(String token);

  Object getCredentialsFromToken(String token);

  boolean getAuthoritiesFromToken(String token);
}
