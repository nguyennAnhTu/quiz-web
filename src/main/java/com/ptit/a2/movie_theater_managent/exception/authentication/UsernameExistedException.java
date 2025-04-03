package com.ptit.a2.movie_theater_managent.exception.authentication;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.CONFLICT_MESSAGE;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.CONFLICT;

public class UsernameExistedException extends BaseException {
  private static final String USERNAME_ALREADY_EXISTED = "Username already existed";

  public UsernameExistedException() {
    super(CONFLICT, CONFLICT_MESSAGE, USERNAME_ALREADY_EXISTED);
  }
}
