package com.ptit.a2.movie_theater_managent.exception.authentication;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BAD_REQUEST_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.INVALID_OTP;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.BAD_REQUEST;

public class InvalidOtpException extends BaseException {
  public InvalidOtpException() {
    super(BAD_REQUEST, BAD_REQUEST_MESSAGE, INVALID_OTP);
  }
}
