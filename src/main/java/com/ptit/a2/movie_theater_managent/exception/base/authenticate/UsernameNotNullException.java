package com.ptit.a2.movie_theater_managent.exception.base.authenticate;


import com.ptit.a2.movie_theater_managent.exception.base.BadRequestException;

public class UsernameNotNullException extends BadRequestException {
  public UsernameNotNullException() {
    super("com.cyai.cms.exception.authenticate.UsernameNotNullException");
  }
}
