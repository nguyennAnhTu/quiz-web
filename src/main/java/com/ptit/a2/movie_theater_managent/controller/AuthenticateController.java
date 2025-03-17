package com.ptit.a2.movie_theater_managent.controller;

import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.facade.AuthenticateFacadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.SUCCESS;

@RestController
@RequestMapping("api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticateController {
  private final AuthenticateFacadeService authenticateFacadeService;

  @PostMapping("/register")
  public ResponseGeneral<AuthRegisterResponse> register(
        @RequestBody @Valid AuthRegisterRequest request
  ) {
    log.info("===Start register");

    return ResponseGeneral.ofCreated(
          SUCCESS,
          authenticateFacadeService.register(request)
    );
  }
}
