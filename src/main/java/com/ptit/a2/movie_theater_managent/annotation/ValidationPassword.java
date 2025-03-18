package com.ptit.a2.movie_theater_managent.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.Message.INVALID_PASSWORD;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidationPassword.PasswordValidator.class)
public @interface ValidationPassword {

  String message() default INVALID_PASSWORD;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class PasswordValidator implements ConstraintValidator<ValidationPassword, String> {

    private static final String PASSWORD_PATTERN = "^.{5,}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
      if (Objects.isNull(value)) return true;

      return value.matches(PASSWORD_PATTERN);
    }
  }
}
