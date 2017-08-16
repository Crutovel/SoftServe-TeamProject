package com.softserve.teamproject.validation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = CopyPasteScheduleValidator.class)
@Documented
public @interface ValidCopyPasteSchedule {

  /**
   * @return message for invalid result
   */
  String message() default "{com.softserve.teamproject.validation.ValidCopyPasteSchedule.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
