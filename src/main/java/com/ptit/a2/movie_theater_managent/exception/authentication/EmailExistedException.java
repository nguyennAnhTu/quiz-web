package com.ptit.a2.movie_theater_managent.exception.authentication;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.CONFLICT_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.EMAIL_ALREADY_EXISTED;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.CONFLICT;

public class EmailExistedException extends BaseException {
  public EmailExistedException() {
    super(CONFLICT, CONFLICT_MESSAGE, EMAIL_ALREADY_EXISTED);
  }
}
