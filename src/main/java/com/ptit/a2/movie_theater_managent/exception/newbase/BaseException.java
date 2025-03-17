package com.ptit.a2.movie_theater_managent.exception.newbase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
  private final int status;
  private final String message;
  private final String details;
}
