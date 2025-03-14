package com.ptit.a2.movie_theater_managent.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.Message.INVALID_USERNAME;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidationUsername.UsernameValidator.class)
public @interface ValidationUsername {

  String message() default INVALID_USERNAME;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};


  class UsernameValidator implements ConstraintValidator<ValidationUsername, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      return value.matches("^[A-Za-z][A-Za-z0-9\\._]*$");
    }
  }
}

