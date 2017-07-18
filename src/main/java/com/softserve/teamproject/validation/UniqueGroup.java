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
 * Use to check if group name is unique when new group is created
 */
@Target({TYPE, ANNOTATION_TYPE, FIELD, METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueGroupValidator.class)
@Documented
public @interface UniqueGroup {

  String message() default "Group name is not unique";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
