package com.ptit.a2.movie_theater_managent.exception.authentication;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.NOT_FOUND_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.USER_NOT_FOUND;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.NOT_FOUND;

public class UserNotFoundException extends BaseException {
  public UserNotFoundException() {
    super(NOT_FOUND, NOT_FOUND_MESSAGE, USER_NOT_FOUND);
  }
}
