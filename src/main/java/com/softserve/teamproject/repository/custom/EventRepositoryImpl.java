package com.softserve.teamproject.repository.custom;

import static com.softserve.teamproject.repository.expression.EventExpressions.eventByEventTypeId;
import static com.softserve.teamproject.repository.expression.EventExpressions.getEventBetweenDates;
import static com.softserve.teamproject.repository.expression.EventExpressions.getEventByGroupId;
import static com.softserve.teamproject.repository.expression.EventExpressions.getEventsBeforeStart;
import static com.softserve.teamproject.repository.expression.EventExpressions.getKeyDates;
import static com.softserve.teamproject.repository.expression.EventExpressions.getNotKeyDates;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.QEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class EventRepositoryImpl extends QueryDslRepositorySupport implements
    EventRepositoryCustom {

  /**
   * Creates a new {@link QueryDslRepositorySupport} instance for the Event type.
   */
  public EventRepositoryImpl() {
    super(Event.class);
  }


  @Override
  public List<Event> getKeyEventsByGroupId(Integer groupId) {
    return from(QEvent.event).where(getKeyDates().and(getEventByGroupId(groupId))).fetch();
  }

  @Override
  public List<Event> getKeyEventsByGroupId(Integer[] groups) {
    return from(QEvent.event).where(getKeyDates().and(getEventByGroupId(groups))).fetch();
  }

  @Override
  public List<Event> getEventsByGroupId(Integer[] groups, LocalDateTime start,
      LocalDateTime finish) {
    return from(QEvent.event)
        .where(getEventByGroupId(groups).and(getEventBetweenDates(start, finish))).fetch();
  }

  @Override
  public List<Event> getEventsByTime(LocalDateTime start,
      LocalDateTime finish) {
    return from(QEvent.event)
        .where((getEventBetweenDates(start, finish))).fetch();
  }

  @Override
  public List<Event> getCrossEvents(LocalDateTime start,
      LocalDateTime finish) {
    List<Event> crossDate = from(QEvent.event)
        .where(getEventBetweenDates(start, finish)).fetch();
    if (crossDate.size() == 0) {
      List<Event> allEventsBeforeStarfrom = from(QEvent.event)
          .where(getEventsBeforeStart(start)).fetch();
      for (Event event : allEventsBeforeStarfrom) {
        if (event.getDateTime().plusMinutes(event.getDuration()).isAfter(start)) {
          crossDate.add(event);
        }
      }
    }
    return crossDate;
  }

  @Override
  public List<Event> getEventsByGroupId(Integer groupId, LocalDateTime start,
      LocalDateTime finish) {
    return from(QEvent.event)
        .where(getEventByGroupId(groupId).and(getEventBetweenDates(start, finish))).fetch();
  }

  public Event getEventByEventTypeId(Integer eventTypeId, Integer groupId) {
    return from(QEvent.event)
        .where(eventByEventTypeId(eventTypeId).and(getEventByGroupId(groupId))).fetchOne();
  }

  @Override
  public List<Event> getNotKeyEventsByGroupId(Integer groupId, LocalDateTime start,
      LocalDateTime finish) {
    return from(QEvent.event).where(getNotKeyDates().and(getEventByGroupId(groupId)
        .and(getEventBetweenDates(start, finish)))).fetch();
  }

}
