package com.ptit.a2.movie_theater_managent.exception.quiz_session;

public class QuizSessionNotFoundException extends RuntimeException {
  public QuizSessionNotFoundException() {
    super("Quiz session not found");
  }
}