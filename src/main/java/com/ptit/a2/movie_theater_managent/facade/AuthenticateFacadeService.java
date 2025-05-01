package com.ptit.a2.movie_theater_managent.facade;

import com.ptit.a2.movie_theater_managent.dto.request.AuthRegisterRequest;
import com.ptit.a2.movie_theater_managent.dto.request.GoogleOAuth2Request;
import com.ptit.a2.movie_theater_managent.dto.request.LoginRequest;
import com.ptit.a2.movie_theater_managent.dto.request.ResendOtpRequest;
import com.ptit.a2.movie_theater_managent.dto.request.VerifyOtpRequest;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.LoginResponse;

public interface AuthenticateFacadeService {
  AuthRegisterResponse register(AuthRegisterRequest request);

  LoginResponse login(LoginRequest request);

  void logout();

  AuthRegisterResponse verifyOtp(VerifyOtpRequest request);

  AuthRegisterResponse resendOtp(ResendOtpRequest request);

  LoginResponse loginWithGoogle(GoogleOAuth2Request request);
}
