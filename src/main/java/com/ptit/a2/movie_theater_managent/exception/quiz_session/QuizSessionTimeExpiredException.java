package com.ptit.a2.movie_theater_managent.exception.quiz_session;

public class QuizSessionTimeExpiredException extends RuntimeException {
  public QuizSessionTimeExpiredException() {
    super("Quiz session has expired");
  }

  public QuizSessionTimeExpiredException(String message) {
    super(message);
  }
}