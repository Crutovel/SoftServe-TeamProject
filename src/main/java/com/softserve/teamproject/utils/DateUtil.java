package com.softserve.teamproject.utils;

import java.time.LocalDate;

public class DateUtil {

  public static LocalDate getMondayDateOfWeek(LocalDate weekDate) {
    return weekDate.minusDays(weekDate.getDayOfWeek().getValue() - 1);
  }

  public static LocalDate getSundayDateOfWeek(LocalDate weekDate) {
    return weekDate.plusDays(7 - weekDate.getDayOfWeek().getValue());
  }
}
