package com.ptit.a2.movie_theater_managent.exception.base.authenticate;


import com.ptit.a2.movie_theater_managent.exception.base.BadRequestException;

public class WrongPasswordException extends BadRequestException {
  public WrongPasswordException(){
    super("com.cyai.cms.exception.authenticate.WrongPasswordException");
  }
}
