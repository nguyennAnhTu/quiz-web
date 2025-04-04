package com.ptit.a2.movie_theater_managent.exception.quiz;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.NOT_FOUND_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.QUIZ_NOT_FOUND;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.NOT_FOUND;

public class QuizNotFoundException extends BaseException {
  public QuizNotFoundException() {
    super(NOT_FOUND, NOT_FOUND_MESSAGE, QUIZ_NOT_FOUND);
  }
}
