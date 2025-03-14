package com.ptit.a2.movie_theater_managent.exception.base;

public class UnauthorizedException extends BaseException {

  private static final String DEFAULT_CODE = "com.ncs.osint.core.exception.base.UnauthorizedException";

  public UnauthorizedException() {
    super(DEFAULT_CODE, "Unauthorized", 401, null);
  }

  public UnauthorizedException(String code) {
    super(code, "", 401, null);
  }
}
