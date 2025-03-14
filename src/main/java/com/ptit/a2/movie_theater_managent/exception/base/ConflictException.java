package com.ptit.a2.movie_theater_managent.exception.base;


import java.util.HashMap;
import java.util.Map;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.BLANK_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.CONFLICT_MESSAGE;
import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.MessageException.DEFAULT_CODE_CONFLICT;
import static com.ptit.a2.movie_theater_managent.exception.base.StatusConstants.CONFLICT;


public class ConflictException extends BaseException {

  public ConflictException(String id, String objectName) {
    super(DEFAULT_CODE_CONFLICT, CONFLICT_MESSAGE, CONFLICT, createParams(id, objectName));
  }

  public ConflictException() {
    super(DEFAULT_CODE_CONFLICT, CONFLICT_MESSAGE, CONFLICT, null);
  }

  public ConflictException(String code) {
    super(code, BLANK_MESSAGE, CONFLICT, null);
  }

  private static Map<String, String> createParams(String id, String objectName) {
    Map<String, String> params = new HashMap<>();
    params.put("id", id);
    params.put("objectName", objectName);
    return params;
  }
}
