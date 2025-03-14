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

import static com.ptit.a2.movie_theater_managent.constanst.MovieTheaterConstants.CommonConstants.INVALID_PHONE_NUMBER;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidationPhoneNumber.PhoneNumberValidator.class)
public @interface ValidationPhoneNumber {

  String message() default INVALID_PHONE_NUMBER;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class PhoneNumberValidator implements ConstraintValidator<ValidationPhoneNumber, String> {

    private static final String PHONE_NUMBER_PATTERN = "(84|0[35789])+(\\d{8})\\b";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

      if (Objects.isNull(value) || value.isEmpty()) return true;


      if (value.startsWith("+84")) {
        value = value.replaceFirst("\\+84", "0");
      }

      return value.matches(PHONE_NUMBER_PATTERN);
    }
  }
}
