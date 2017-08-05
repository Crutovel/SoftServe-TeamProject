package com.softserve.teamproject.validation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = CopyPasteScheduleValidator.class)
@Documented
public @interface ValidCopyPasteSchedule {

  /**
   * @return message for invalid result
   */
  String message() default "Invalid arguments";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
