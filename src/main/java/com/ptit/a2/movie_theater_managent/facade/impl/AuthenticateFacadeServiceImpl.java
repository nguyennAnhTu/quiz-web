package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.constanst.enums.TokenType;
import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.request.LoginRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.LoginResponse;
import com.ptit.a2.movie_theater_managent.entity.User;
import com.ptit.a2.movie_theater_managent.exception.base.authenticate.PasswordIncorrectException;
import com.ptit.a2.movie_theater_managent.facade.AuthenticateFacadeService;
import com.ptit.a2.movie_theater_managent.service.JwtTokenService;
import com.ptit.a2.movie_theater_managent.service.TokenRedisService;
import com.ptit.a2.movie_theater_managent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.CLAIM_AUTHORITIES_KEY;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.CLAIM_EMAIL_KEY;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.RedisConstant.ACCESS_TOKEN_KEY;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.RedisConstant.REFRESH_TOKEN_KEY;
import static com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils.getCurrentUserId;
import static com.ptit.a2.movie_theater_managent.utils.AuthenticationUtils.getDefaultAuthorities;
import static com.ptit.a2.movie_theater_managent.utils.PasswordEncoderUtils.getPasswordEncoder;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticateFacadeServiceImpl implements AuthenticateFacadeService {
  private final UserService userService;
  private final JwtTokenService jwtTokenService;
  private final TokenRedisService tokenRedisService;

  @Override
  @Transactional
  public AuthRegisterResponse register(AuthRegisterRequest request) {
    log.info("(register) request: {}", request);

    return userService.create(request);
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    log.info("===start login");

    User user = userService.findByEmail(request.getEmail());
    this.equalPassword(request.getPassword(), user.getPassword());

    String accessToken = jwtTokenService.generateToken(
          user.getId().toString(),
          this.buildClaimsForToken(user, TokenType.ACCESS_TOKEN),
          TokenType.ACCESS_TOKEN
    );

    String refreshToken = jwtTokenService.generateToken(
          user.getId().toString(),
          this.buildClaimsForToken(user, TokenType.REFRESH_TOKEN),
          TokenType.REFRESH_TOKEN
    );

    tokenRedisService.hashSet(ACCESS_TOKEN_KEY, user.getId().toString(), accessToken);
    tokenRedisService.hashSet(REFRESH_TOKEN_KEY, user.getId().toString(), refreshToken);

    return LoginResponse.of(
          accessToken,
          refreshToken
    );
  }

  @Override
  public void logout() {
    log.info("===start logout");

    tokenRedisService.remove(
          getCurrentUserId().toString()
    );
  }

  private void equalPassword(String passwordRaw, String passwordEncrypted) {
    if (!getPasswordEncoder().matches(passwordRaw, passwordEncrypted)) {
      throw new PasswordIncorrectException();
    }
  }

  private Map<String, Object> buildClaimsForToken(final User user, TokenType tokenType) {
    Map<String, Object> claims = new HashMap<>();

    claims.put(CLAIM_EMAIL_KEY, user.getEmail());

    if (tokenType == TokenType.ACCESS_TOKEN) {
      claims.put(CLAIM_AUTHORITIES_KEY, user.getIsAdmin());
    }

    log.info("(buildClaimsForToken) claims: {}", claims);
    return claims;
  }
}
