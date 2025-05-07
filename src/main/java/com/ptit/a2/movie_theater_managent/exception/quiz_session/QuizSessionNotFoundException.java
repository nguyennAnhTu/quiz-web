package com.ptit.a2.movie_theater_managent.exception.quiz_session;

import com.ptit.a2.movie_theater_managent.exception.base.NotFoundException;
import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.NOT_FOUND;

public class QuizSessionNotFoundException extends BaseException {
  public QuizSessionNotFoundException() {
    super(NOT_FOUND, "NOT FOUND", "Quiz session not found");
  }
}