package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.facade.AuthenticateFacadeService;
import com.ptit.a2.movie_theater_managent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticateFacadeServiceImpl implements AuthenticateFacadeService {
  private final UserService userService;

  @Override
  @Transactional
  public AuthRegisterResponse register(AuthRegisterRequest request) {
    log.info("(register) request: {}", request);

    return userService.create(request);
  }
}
