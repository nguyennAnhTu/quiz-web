package com.ptit.a2.movie_theater_managent.exception.base.authenticate;


import com.ptit.a2.movie_theater_managent.exception.base.BadRequestException;

public class PasswordIncorrectException extends BadRequestException {
  private static final String DEFAULT_CODE = "com.cyai.cms.exception.authenticate.PasswordIncorrectException";

  public PasswordIncorrectException() {
    super(DEFAULT_CODE);
  }
}
