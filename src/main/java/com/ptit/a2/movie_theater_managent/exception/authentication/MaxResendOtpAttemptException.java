package com.ptit.a2.movie_theater_managent.exception.authentication;

import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.*;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.BAD_REQUEST;

public class MaxResendOtpAttemptException extends BaseException {
  public MaxResendOtpAttemptException() {
    super(BAD_REQUEST, BAD_REQUEST_MESSAGE, MAX_RESEND_OTP_ATTEMPTS);
  }
}
