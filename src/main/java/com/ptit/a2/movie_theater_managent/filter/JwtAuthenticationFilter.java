package com.ptit.a2.movie_theater_managent.filter;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;
import com.ptit.a2.movie_theater_managent.service.JwtTokenService;
import com.ptit.a2.movie_theater_managent.service.TokenRedisService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BEARER_TOKEN_TYPE_START;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.RedisConstant.ACCESS_TOKEN_KEY;
import static com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils.getDefaultAuthorities;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {//
  private final TokenRedisService tokenRedisService;
  private final JwtTokenService jwtTokenService;

  protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    log.debug("(doFilterInternal) request: {}", request);

    final String accessTokenBearer = request.getHeader(AUTHORIZATION);
    if (accessTokenBearer == null || !accessTokenBearer.startsWith(BEARER_TOKEN_TYPE_START)) {
      log.debug("Token invalid or null, token: {}", accessTokenBearer);

      filterChain.doFilter(request, response);
      return;
    }

    String accessToken = accessTokenBearer.substring(BEARER_TOKEN_TYPE_START.length());
    try {
      Integer userId = Integer.parseInt((String) jwtTokenService.getPrincipalFromToken(accessToken));
      String email = (String) jwtTokenService.getCredentialsFromToken(accessToken);

      String accessTokenOnRedis = (String) tokenRedisService.hashGet(ACCESS_TOKEN_KEY, userId.toString());

      if (!Objects.equals(accessToken, accessTokenOnRedis)) {
        log.debug("Token not in system");

        filterChain.doFilter(request, response);

        return;
      }

      boolean isAdmin = jwtTokenService.getAuthoritiesFromToken(accessToken);

      log.debug("isAdmin: {}", isAdmin);

      List<GrantedAuthority> authorities = isAdmin
            ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            : List.of(new SimpleGrantedAuthority("ROLE_USER"));

      log.info("(doFilterInternal) authorities: {}", authorities);

      Authentication authentication = new UsernamePasswordAuthenticationToken(
            userId,
            email,
            authorities
      );

      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      handleException(e, response, HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
    } catch (SignatureException | MalformedJwtException e) {
      handleException(e, response, HttpServletResponse.SC_UNAUTHORIZED, "Token invalid");
    } catch (BaseException e) {
      handleException(e, response, e.getStatus(), e.getMessage());
    } catch (Exception e) {
      handleException(e, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
    }
  }

  private void handleException(Exception e, HttpServletResponse response, int status, String message) throws IOException {
    log.error("(doFilterInternal): {}", e.getMessage());

    response.sendError(status, message);
  }
}
