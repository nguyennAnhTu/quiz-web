package com.ptit.a2.movie_theater_managent.exception.base;

import java.util.HashMap;
import java.util.Map;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BAD_REQUEST_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BLANK_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.MessageException.DEFAULT_CODE_BAD_REQUEST;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.BAD_REQUEST;


public class BadRequestException extends BaseException {
  public BadRequestException() {
    super(DEFAULT_CODE_BAD_REQUEST, BAD_REQUEST_MESSAGE, BAD_REQUEST, null);
  }

  public BadRequestException(String code) {
    super(code, BLANK_MESSAGE, BAD_REQUEST, null);
  }

  public BadRequestException(String id, String objectName) {
    super(DEFAULT_CODE_BAD_REQUEST, BAD_REQUEST_MESSAGE, BAD_REQUEST, createParams(id, objectName));
  }

  private static Map<String, String> createParams(String id, String objectName) {
    Map<String, String> params = new HashMap<>();
    params.put("id", id);
    params.put("objectName", objectName);
    return params;
  }
}
