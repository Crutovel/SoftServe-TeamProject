package com.softserve.teamproject.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtil {

  public static LocalDate getMondayDateOfWeek(LocalDate weekDate) {
    TemporalField temporalField = WeekFields.of(Locale.forLanguageTag("ru")).dayOfWeek();
    return weekDate.with(temporalField, 1);
  }

  public static LocalDate getSundayDateOfWeek(LocalDate weekDate) {
    TemporalField temporalField = WeekFields.of(Locale.forLanguageTag("ru")).dayOfWeek();
    return weekDate.with(temporalField, 7);
  }


  public static List<LocalDate> getWorkWeekOfDate(LocalDate date){
    TemporalField temporalField = WeekFields.of(Locale.forLanguageTag("ru")).dayOfWeek();
    LocalDate startOfWeek = date.with(temporalField, 1);
    LocalDate endOfWeek = startOfWeek.with(temporalField, 5);
    return getDateRange(startOfWeek, endOfWeek);
  }

  public static List<LocalDate> getDateRange(LocalDate start, LocalDate end){
    return Stream.iterate(start, date -> date.plusDays(1))
        .limit(ChronoUnit.DAYS.between(start, end.plusDays(1)))
        .collect(Collectors.toList());
  }
}
