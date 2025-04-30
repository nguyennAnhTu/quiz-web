package com.ptit.a2.movie_theater_managent.facade.impl;

import com.ptit.a2.movie_theater_managent.constanst.enums.TokenType;
import com.ptit.a2.movie_theater_managent.dto.request.*;
import com.ptit.a2.movie_theater_managent.dto.response.AuthRegisterResponse;
import com.ptit.a2.movie_theater_managent.dto.response.LoginResponse;
import com.ptit.a2.movie_theater_managent.dto.response.MediaResponse;
import com.ptit.a2.movie_theater_managent.entity.User;
import com.ptit.a2.movie_theater_managent.exception.authentication.EmailExistedException;
import com.ptit.a2.movie_theater_managent.exception.authentication.MaxOtpAttemptException;
import com.ptit.a2.movie_theater_managent.exception.authentication.PasswordIncorrectException;
import com.ptit.a2.movie_theater_managent.exception.authentication.UserNotFoundException;
import com.ptit.a2.movie_theater_managent.facade.AuthenticateFacadeService;
import com.ptit.a2.movie_theater_managent.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.AuthConstant.*;
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
  private final EmailService emailService;
  private final OtpService otpService;
  private final MediaService mediaService;

  private static final String OTP_SENT_MESSAGE = "Otp has been sent";
  private static final String SUCCESS_MESSAGE = "Register successfully";
  private static final String DEFAULT_URL_AVATAR = "https://res.cloudinary.com/diorkbloc/image/upload/v1745505401/4b905b1342b5635310923fd10319c265_y36zy1.jpg";

  @Override
  @Transactional
  public AuthRegisterResponse register(AuthRegisterRequest request) {
    log.info("(register) request: {}", request);

    Optional<User> userOptional = userService.find(request.getEmail());
    if (userOptional.isEmpty()) {
      MediaResponse mediaResponse = mediaService.create(
            MediaRequest.of(DEFAULT_URL_AVATAR, 1F, 0F, 0F)
      );
      userService.createInactiveUser(request, mediaResponse.getId());
      this.sendOtp(request.getEmail());
    } else {
      User user = userOptional.get();
      if (Boolean.TRUE.equals(user.getIsActive())) {
        throw new EmailExistedException();
      }

      userService.updateInformation(user.getId(), request.getPassword(), request.getUsername());
      this.sendOtp(request.getEmail());
    }

    return AuthRegisterResponse.of(
          request.getEmail(),
          OTP_SENT_MESSAGE
    );
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

  @Override
  @Transactional
  public AuthRegisterResponse verifyOtp(VerifyOtpRequest request) {
    log.info("(verifyOtp) request: {}", request);

    try {
      otpService.validateOtp(request);
      if (Boolean.TRUE.equals(request.getIsRegister())) {
        userService.activeUser(request.getEmail());
      }
      otpService.clearOtpData(request.getEmail());

      return AuthRegisterResponse.of(
            request.getEmail(),
            SUCCESS_MESSAGE
      );
    } catch (Exception e) {
      // Kiểm tra nếu là lỗi vượt quá số lần thử
      if (e instanceof MaxOtpAttemptException) {
        log.info("start delete");
        // Nếu là quá trình đăng ký, xóa user chưa active
        if (Boolean.TRUE.equals(request.getIsRegister())) {
          userService.deleteInactiveUser(request.getEmail());
        }
        // Xóa OTP data
        otpService.clearOtpData(request.getEmail());
        throw e;
      }
      throw e;
    }
  }

  @Override
  public AuthRegisterResponse resendOtp(ResendOtpRequest request) {
    log.info("(resendOtp) email: {}", request.getEmail());

    // Kiểm tra tài khoản inactive
    userService.findUserByEmail(request.getEmail());

    // Kiểm tra giới hạn gửi lại OTP
    otpService.checkResendLimit(request.getEmail());

    // Xóa OTP cũ
    otpService.clearOtpData(request.getEmail());

    // Tạo và gửi OTP mới
    String otp = otpService.generateOtp(request.getEmail());
    emailService.sendOtpEmail(request.getEmail(), otp);

    return AuthRegisterResponse.of(
          request.getEmail(),
          OTP_SENT_MESSAGE
    );
  }

  private void equalPassword(String passwordRaw, String passwordEncrypted) {
    if (!getPasswordEncoder().matches(passwordRaw, passwordEncrypted)) {
      throw new PasswordIncorrectException();
    }
  }

  private Map<String, Object> buildClaimsForToken(final User user, TokenType tokenType) {
    Map<String, Object> claims = new HashMap<>();

    claims.put(CLAIM_ID_KEY, user.getId());
    claims.put(CLAIM_EMAIL_KEY, user.getEmail());

    if (tokenType == TokenType.ACCESS_TOKEN) {
      claims.put(CLAIM_AUTHORITIES_KEY, user.getIsAdmin());
    }

    log.info("(buildClaimsForToken) claims: {}", claims);
    return claims;
  }

  private void sendOtp(String email) {
    String otp = otpService.generateOtp(email);
    emailService.sendOtpEmail(email, otp);
  }
}
