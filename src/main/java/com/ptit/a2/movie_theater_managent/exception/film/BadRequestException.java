package com.ptit.a2.movie_theater_managent.exception.film;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.MessageException.DEFAULT_CODE_BAD_REQUEST;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.StatusException.BAD_REQUEST;


public class BadRequestException extends BaseException {
  public BadRequestException(){
    super(BAD_REQUEST, DEFAULT_CODE_BAD_REQUEST, DEFAULT_CODE_BAD_REQUEST);
  }
}
