package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.constanst.enums.TokenType;
import com.ptit.a2.movie_theater_managent.exception.authentication.TokenExpiredException;
import com.ptit.a2.movie_theater_managent.exception.base.authenticate.TokenInvalidException;
import com.ptit.a2.movie_theater_managent.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.CLAIMS;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.CLAIM_AUTHORITIES_KEY;

@Slf4j
@Service
public class JwtTokenServiceImpl implements JwtTokenService {
  @Value("${jwt.secret-key}")
  private String secretKey;
  @Value("${jwt.access-token.ttl}")
  private long expireTimeAccessToken;
  @Value("${jwt.refresh-token.ttl}")
  public long expireTimeRefreshToken;

  @Override
  public String generateToken(String userId, Map<String, Object> claims, TokenType tokenType) {
    log.info("(generateAccessToken) userId: {}, claims: {}", userId, claims);

    if (tokenType == TokenType.ACCESS_TOKEN) {
      return this.generateToken(userId, claims, expireTimeAccessToken);
    } else {
      return this.generateToken(userId, claims, expireTimeRefreshToken);
    }
  }

  @Override
  public Object getPrincipalFromToken(String token) {
    log.debug("(getPrincipalFromToken) start");

    this.validateToken(token);

    return this.getClaims(token).getSubject();
  }

  @Override
  public Object getCredentialsFromToken(String token) {
    log.debug("(getCredentialsFromToken) start");

    this.validateToken(token);

    return this.getClaims(token).getAudience();
  }

  @Override
  public List getAuthoritiesFromToken(String token) {
    log.debug("(getAuthoritiesFromToken) start");

    this.validateToken(token);

    return this.getClaims(token).get(CLAIM_AUTHORITIES_KEY, List.class);
  }

  private String generateToken(String subject, Map<String, Object> claims, long tokenLifeTime) {
    log.debug("(generateToken) start");

    return Jwts.builder()
          .setSubject(subject)
          .claim(CLAIMS, claims)
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + tokenLifeTime))
          .signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
          .compact();
  }

  public void validateToken(String token) {
    log.info("(validateToken)start");
    if (!isValidToken(token)) {
      log.error("(validateToken) ==========> TokenInvalidException");
      throw new TokenInvalidException();
    }

    if (isExpiredToken(token)) {
      log.error("(validateToken) ==========> TokenExpiredException");
      throw new TokenExpiredException();
    }
  }

  public boolean isValidToken(String token) {
    try {
      Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
            .build()
            .parseClaimsJws(token);

      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public boolean isExpiredToken(String token) {
    return getClaims(token).getExpiration().before(new Date());
  }

  private Claims getClaims(String token) {
    return Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
          .build()
          .parseClaimsJws(token)
          .getBody();
  }
}
