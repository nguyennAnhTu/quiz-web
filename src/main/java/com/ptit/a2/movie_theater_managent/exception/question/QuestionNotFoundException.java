package com.ptit.a2.movie_theater_managent.exception.question;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.NOT_FOUND_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.QUESTION_NOT_FOUND;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.NOT_FOUND;

public class QuestionNotFoundException extends BaseException {
  public QuestionNotFoundException() {
    super(NOT_FOUND, NOT_FOUND_MESSAGE, QUESTION_NOT_FOUND);
  }
}
