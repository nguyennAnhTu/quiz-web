package com.ptit.a2.movie_theater_managent.advice;


import com.ptit.a2.movie_theater_managent.dto.Error;
import com.ptit.a2.movie_theater_managent.dto.ResponseGeneral;
import com.ptit.a2.movie_theater_managent.exception.newbase.BaseException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.*;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerAdvice {
  private final MessageSource messageSource;

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ResponseGeneral<Error>> handleBaseException(BaseException exception) {
    return ResponseEntity
          .status(exception.getStatus())
          .body(this.getError(
                exception.getStatus(),
                exception.getMessage(),
                exception.getDetails()
          ));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseGeneral<Error>> handleValidationExceptions(
        MethodArgumentNotValidException exception
  ) {
    List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
    Map<String, String> errors = new HashMap<>();

    for (FieldError fieldError : fieldErrors) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(ResponseGeneral.of(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_MESSAGE, new Error(errors)));
  }

  private ResponseGeneral<Error> getError(int status, String message, String detail) {
    return ResponseGeneral.of(
          status, message, new Error(detail)
    );
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ResponseGeneral<Error>> handleConstraintViolationExceptions(
        ConstraintViolationException exception,
        WebRequest webRequest
  ) {
    log.error("(handleConstraintViolationExceptions) exception: {}", exception.getMessage());
    String language = Objects.nonNull(webRequest.getHeader(LANGUAGE)) ?
          webRequest.getHeader(LANGUAGE) : DEFAULT_LANGUAGE;

    String errorMessage = exception.getConstraintViolations().stream()
          .map(constraintViolation -> constraintViolation.getMessage())
          .findFirst()
          .orElse(exception.getMessage());

    log.error("(handleConstraintViolationExceptions) {}", errorMessage);
    return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(getError(HttpStatus.BAD_REQUEST.value(), errorMessage, language));
  }

  private ResponseGeneral<Error> getError(int status, String code, Map<String, String> params) {
    return ResponseGeneral.of(
          status,
          HttpStatus.valueOf(status).getReasonPhrase(),
          Error.of(code, params)
    );
  }

  private ResponseGeneral<Error> getError(int status, String code, Locale locale, Map<String, String> params) {
    return ResponseGeneral.of(
          status,
          HttpStatus.valueOf(status).getReasonPhrase(),
          Error.of(code, getMessage(code, locale, params))
    );
  }

  private String getMessage(String code, Locale locale, Map<String, String> params) {
    var message = getMessage(code, locale);
    if (params != null && !params.isEmpty()) {
      for (var param : params.entrySet()) {
        message = message.replace(getMessageParamsKey(param.getKey()), param.getValue());
      }
    }
    return message;
  }

  private String getMessage(String code, Locale locale) {
    try {
      return messageSource.getMessage(code, null, locale);
    } catch (Exception ex) {
      return code;
    }
  }

  private String getMessageParamsKey(String key) {
    return PERCENT + key + PERCENT;
  }
}
