package com.softserve.teamproject.repository.custom;

import static com.softserve.teamproject.repository.expression.EventExpressions.eventByEventTypeId;
import static com.softserve.teamproject.repository.expression.EventExpressions.getEventBetweenDates;
import static com.softserve.teamproject.repository.expression.EventExpressions.getEventByGroupId;
import static com.softserve.teamproject.repository.expression.EventExpressions.getKeyDates;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.QEvent;
import java.time.LocalDateTime;
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
  public List<Event> getKeyEventsByGroupId(List<Integer> groups) {
    return from(QEvent.event).where(getKeyDates().and(getEventByGroupId(groups))).fetch();
  }

  @Override
  public List<Event> getEventsByGroupId(List<Integer> groups, LocalDateTime start,
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
  public List<Event> getEventsByGroupId(Integer groupId, LocalDateTime start,
      LocalDateTime finish) {
    return from(QEvent.event)
        .where(getEventByGroupId(groupId).and(getEventBetweenDates(start, finish))).fetch();
  }

  public Event getEventByEventTypeId(Integer eventTypeId,Integer groupId) {
    return from(QEvent.event)
        .where(eventByEventTypeId(eventTypeId).and(getEventByGroupId(groupId))).fetchOne();
  }

}
