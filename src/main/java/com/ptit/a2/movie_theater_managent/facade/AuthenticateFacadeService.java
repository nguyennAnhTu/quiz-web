package com.ptit.a2.movie_theater_managent.facade;

import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.request.LoginRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.LoginResponse;

public interface AuthenticateFacadeService {
  AuthRegisterResponse register(AuthRegisterRequest request);

  LoginResponse login(LoginRequest request);

  void logout();
}
