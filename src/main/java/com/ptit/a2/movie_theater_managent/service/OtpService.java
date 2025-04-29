package com.ptit.a2.movie_theater_managent.service;

import com.ptit.a2.movie_theater_managent.dto.request.VerifyOtpRequest;

public interface OtpService {
  String generateOtp(String email);

  void validateOtp(VerifyOtpRequest request);

  void clearOtpData(String email);

  void checkResendLimit(String email);
}
