package com.softserve.teamproject.validation;

import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Use for check every string value in Set of String is match to given constraints
 */
public class StringConstraintInSetValidator implements
  ConstraintValidator<StringConstraintInSet, Set<String>> {

  StringConstraintInSet constraint;

  public void initialize(StringConstraintInSet constraint) {
    this.constraint = constraint;
  }

  /**
   * Check every string value in Set of String is match to given in StringConstraintInSet
   * constraints
   *
   * @param obj Set of Strings for check
   * @param context Validator context
   * @return true if every string value in Set of String is match to given constraints
   */
  public boolean isValid(Set<String> obj, ConstraintValidatorContext context) {
    return obj.stream().allMatch(
      str ->
        str.length() >= constraint.min()
          &&
          str.length() <= constraint.max()
          &&
          str.matches(constraint.regexp())
    );
  }
}
