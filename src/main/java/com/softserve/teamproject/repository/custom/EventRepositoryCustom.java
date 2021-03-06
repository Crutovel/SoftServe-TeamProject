package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.Event;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {

  List<Event> getKeyEventsByGroupId(Integer groupId);

  List<Event> getKeyEventsByGroupId(List<Integer> groups);

  List<Event> getEventsByGroupId(List<Integer> groups, LocalDateTime start, LocalDateTime finish);

  List<Event> getEventsByGroupId(Integer groupId, LocalDateTime start, LocalDateTime finish);

  List<Event> getEventsByTime(LocalDateTime start, LocalDateTime finish);

  Event getCrossEvents(LocalDateTime start, LocalDateTime finish, Integer roomId);

  Event getCrossEvents(LocalDateTime start, LocalDateTime finish, Integer roomId, Integer eventId);

  Event getEventByEventTypeId(Integer eventTypeId, Integer groupId);

  List<Event> getNotKeyEventsByGroupId(Integer groupId, LocalDateTime start,
      LocalDateTime finish);

  List<Event> getEventsByGroupId(Integer groupId);
}
