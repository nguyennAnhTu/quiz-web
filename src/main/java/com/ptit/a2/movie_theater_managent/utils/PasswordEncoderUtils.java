package com.ptit.a2.movie_theater_managent.utils;


import com.ptit.a2.movie_theater_managent.exception.base.authenticate.PasswordIncorrectException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderUtils {
  public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
  public static PasswordEncoder getPasswordEncoder() {
    return PASSWORD_ENCODER;
  }

  public static void equalPassword(String passwordRaw, String passwordEncrypted) {
    if (!getPasswordEncoder().matches(passwordRaw, passwordEncrypted)) {
      throw new PasswordIncorrectException();
    }
  }

}
