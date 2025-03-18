package com.ptit.a2.movie_theater_managent.exception.authentication;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BAD_REQUEST_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.EXPIRED_TOKEN;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.BAD_REQUEST;

public class TokenExpiredException extends BaseException {
  public TokenExpiredException() {
    super(BAD_REQUEST, BAD_REQUEST_MESSAGE, EXPIRED_TOKEN);
  }
}
