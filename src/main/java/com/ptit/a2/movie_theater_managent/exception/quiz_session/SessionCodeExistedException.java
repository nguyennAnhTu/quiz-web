package com.ptit.a2.movie_theater_managent.exception.quiz_session;

public class SessionCodeExistedException extends RuntimeException {
  public SessionCodeExistedException() {
    super("Session code already exists");
  }
}
