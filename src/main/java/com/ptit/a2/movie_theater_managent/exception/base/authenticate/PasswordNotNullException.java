package com.ptit.a2.movie_theater_managent.exception.base.authenticate;


import com.ptit.a2.movie_theater_managent.exception.base.BadRequestException;

public class PasswordNotNullException extends BadRequestException {
  public PasswordNotNullException(){
    super("com.cyai.cms.exception.authenticate.PasswordNotNullException");
  }
}
