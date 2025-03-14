package com.ptit.a2.movie_theater_managent.exception.base.authenticate;


import com.ptit.a2.movie_theater_managent.exception.base.UnauthorizedException;

public class TokenInvalidException extends UnauthorizedException {
  public TokenInvalidException() {
    super("com.cyai.cms.exception.authenticate.TokenValidException");
  }
}
