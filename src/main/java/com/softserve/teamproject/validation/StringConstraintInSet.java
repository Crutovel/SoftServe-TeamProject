package com.softserve.teamproject.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Use for check every string value in Set of String
 */
@Target({TYPE, ANNOTATION_TYPE, FIELD, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = StringConstraintInSetValidator.class)
@Documented
public @interface StringConstraintInSet {

  String message() default "Set of strings is not match";

  /**
   * @return the regular expression to match
   */
  String regexp() default ".*";

  /**
   * @return size the element must be higher or equal to
   */
  int min() default 0;

  /**
   * @return size the element must be lower or equal to
   */
  int max() default Integer.MAX_VALUE;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
