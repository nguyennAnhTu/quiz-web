package com.ptit.a2.movie_theater_managent.service.impl;

import com.ptit.a2.movie_theater_managent.dto.request.VerifyOtpRequest;
import com.ptit.a2.movie_theater_managent.exception.authentication.InvalidOtpException;
import com.ptit.a2.movie_theater_managent.exception.authentication.MaxOtpAttemptException;
import com.ptit.a2.movie_theater_managent.exception.authentication.MaxResendOtpAttemptException;
import com.ptit.a2.movie_theater_managent.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
  private final RedisTemplate<String, Object> redisTemplate;

  private static final String OTP_PREFIX = "user:otp:";
  private static final String OTP_ATTEMPT_PREFIX = "user:otp:attempt:";
  private static final int OTP_EXPIRATION = 5;
  private static final int MAX_OTP_ATTEMPTS = 5;
  private static final int MAX_RESEND_ATTEMPTS = 3;
  private static final String RESEND_PREFIX = "user:otp:resend:";

  @Override
  public String generateOtp(String email) {
    log.info("(generateOtp) email: {}", email);

    String otp = String.format("%06d", new Random().nextInt(999999));

    String key = OTP_PREFIX + email;
    redisTemplate.opsForValue().set(key, otp);
    redisTemplate.expire(key, OTP_EXPIRATION, TimeUnit.MINUTES);

    log.info("attempt");

    String attemptKey = OTP_ATTEMPT_PREFIX + email;
    redisTemplate.opsForValue().set(attemptKey, String.valueOf(0));
    redisTemplate.expire(attemptKey, OTP_EXPIRATION, TimeUnit.MINUTES);

    this.incrementResendAttempts(email);

    return otp;
  }

  @Override
  public void validateOtp(VerifyOtpRequest request) {
    log.info("(validateOtp) email: {}", request.getEmail());

    this.checkOtpAttempts(request.getEmail());

    if (!this.isValidOtp(request.getEmail(), request.getOtp())) {
      this.incrementOtpAttempts(request.getEmail());
      throw new InvalidOtpException();
    }
  }

  @Override
  public void clearOtpData(String email) {
    String key = OTP_PREFIX + email;
    String attemptKey = OTP_ATTEMPT_PREFIX + email;

    redisTemplate.delete(key);
    redisTemplate.delete(attemptKey);
  }

  @Override
  public void checkResendLimit(String email) {
    log.info("(checkResendLimit) email: {}", email);

    String resendKey = RESEND_PREFIX + email;
    String resendValue = (String) redisTemplate.opsForValue().get(resendKey);

    if (resendValue != null) {
      int resendAttempts = Integer.parseInt(resendValue);
      if (resendAttempts >= MAX_RESEND_ATTEMPTS) {
        throw new MaxResendOtpAttemptException();
      }
    }
  }

  private boolean isValidOtp(String email, String otp) {
    String key = OTP_PREFIX + email;

    String otpOnRedis = (String) redisTemplate.opsForValue().get(key);

    if (otpOnRedis == null) {
      return false;
    }

    return otp.equals(otpOnRedis);
  }

  private void incrementOtpAttempts(String email) {
    String attemptKey = OTP_ATTEMPT_PREFIX + email;
    String attemptValue = (String) redisTemplate.opsForValue().get(attemptKey);
    if (attemptValue != null) {
      int attempts = Integer.parseInt(attemptValue) + 1;
      redisTemplate.opsForValue().set(attemptKey, String.valueOf(attempts));
    }
  }

  private void checkOtpAttempts(String email) {
    String attemptKey = OTP_ATTEMPT_PREFIX + email;
    String attemptValue = (String) redisTemplate.opsForValue().get(attemptKey);

    if (attemptValue == null) {
      throw new InvalidOtpException();
    }

    int attempts = Integer.parseInt(attemptValue);
    if (attempts >= MAX_OTP_ATTEMPTS-1) {
      this.clearOtpData(email);
      throw new MaxOtpAttemptException();
    }
  }

  private void incrementResendAttempts(String email) {
    String resendKey = RESEND_PREFIX + email;
    String resendValue = (String) redisTemplate.opsForValue().get(resendKey);
    int resendAttempts = resendValue != null ? Integer.parseInt(resendValue) + 1 : 0;

    redisTemplate.opsForValue().set(resendKey, String.valueOf(resendAttempts));
    redisTemplate.expire(resendKey, OTP_EXPIRATION, TimeUnit.MINUTES);
  }
}
