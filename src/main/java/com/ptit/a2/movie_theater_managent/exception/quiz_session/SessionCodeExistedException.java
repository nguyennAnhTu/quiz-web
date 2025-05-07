package com.ptit.a2.movie_theater_managent.exception.quiz_session;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.CONFLICT;

public class SessionCodeExistedException extends BaseException {
  public SessionCodeExistedException() {
    super(CONFLICT, "CONFLICT", "Session code already exists");
  }
}
