package com.ptit.a2.movie_theater_managent.exception.film;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.GENRE_NOT_FOUND;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.NOT_FOUND_MESSAGE;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.NOT_FOUND;

public class GenreNotFoundException extends BaseException {
  public GenreNotFoundException() {
    super(NOT_FOUND, NOT_FOUND_MESSAGE, GENRE_NOT_FOUND);
  }
}
