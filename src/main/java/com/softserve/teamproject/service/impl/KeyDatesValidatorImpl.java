package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Template;
import com.softserve.teamproject.repository.TemplateRepository;
import com.softserve.teamproject.service.KeyDatesValidator;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class made to generate valid key dates according to generation strategy and validate incoming
 * events
 */
@Service
public class KeyDatesValidatorImpl implements KeyDatesValidator {

  @Autowired
  TemplateRepository templateRepository;

  /**
   * Generate dates, which are pointing to demo week
   *
   * @param group the group to generate dates for
   * @return map that contains EventType as key and generated LocalDate as value
   */
  public Map<EventType, LocalDate> generateDates(Group group) {
    Map<EventType, LocalDate> keyDates = new HashMap<>();
    if (group.getStartDate() == null) {
      throw new IllegalArgumentException("Group start date must be specified");
    }
    LocalDate startDate = group.getStartDate();
    List<Template> templates = group.getSpecialization().getStrategy().getTemplates();
    for (Template template : templates) {
      LocalDate newDate = null;
      if (template.getDuration() == 0) {
        newDate = group.getFinishDate();
      } else if (template.getDuration() > 0) {
        newDate = startDate.plusDays(template.getDuration());
      }
      keyDates.put(template.getEventType(), newDate);
      startDate = newDate;
    }
    return keyDates;
  }

  /**
   * Validates input event according to <code>validDates</code> parameter
   *
   * @param event event to validate
   * @param validDates map with valid dates for group we want to add key event to.
   * @throws IllegalArgumentException if event type is incorrect or date is invalid
   */
  public void validateKeyDate(Event event, Map<EventType, LocalDate> validDates) {
    TemporalField temporalField = WeekFields.of(Locale.US).dayOfWeek();
    if (event.getEventType() == null || !validDates.containsKey(event.getEventType())) {
      throw new IllegalArgumentException("Incorrect Event Type");
    }
    LocalDate startOfWeek = validDates.get(event.getEventType()).with(temporalField, 1);
    LocalDate endOfWeek = startOfWeek.with(temporalField, 7);
    LocalDate eventDate = event.getDateTime().toLocalDate();
    if (!(eventDate.isAfter(startOfWeek) && eventDate.isBefore(endOfWeek))) {
      throw new IllegalArgumentException("Wrong date specified");
    }
  }
}
