package com.ptit.a2.movie_theater_managent.exception.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusConstants {
  public static final Integer NOT_FOUND = 404;
  public static final Integer CONFLICT = 409;
  public static final Integer BAD_REQUEST = 400;
  public static final Integer SERVER_ERROR = 500;
}
