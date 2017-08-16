package com.softserve.teamproject.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = KeyDatesValidator.class)
@Documented
public @interface KeyDates {
  String message() default "{com.softserve.teamproject.validation.KeyDates.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
