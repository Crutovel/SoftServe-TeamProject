package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Group;
import java.time.LocalDate;
import java.util.Map;

public interface KeyDatesValidator {

  Map<EventType, LocalDate> generateDates(Group group);

  void validateKeyDate(Event event, Map<EventType, LocalDate> validDates);
}
