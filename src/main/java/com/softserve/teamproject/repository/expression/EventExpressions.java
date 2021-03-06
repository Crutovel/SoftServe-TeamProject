package com.softserve.teamproject.repository.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.softserve.teamproject.entity.QEvent;
import com.softserve.teamproject.entity.Room;
import java.time.LocalDateTime;
import java.util.List;

public class EventExpressions {

  public static BooleanExpression getKeyDates() {
    return QEvent.event.eventType.isKeyDate.eq(true);
  }

  public static BooleanExpression getEventByGroupId(Integer id) {
    return QEvent.event.group.id.eq(id);
  }

  public static BooleanExpression getEventByGroupId(List<Integer> groups) {
    return QEvent.event.group.id.in(groups);
  }

  public static BooleanExpression getEventBetweenDates(LocalDateTime start, LocalDateTime finish) {
    return QEvent.event.start.between(start, finish);
  }

  public static BooleanExpression getEventsBeforeStart(LocalDateTime start) {
    return QEvent.event.start.before(start);
  }

  public static BooleanExpression eventByEventTypeId(Integer id) {
    return QEvent.event.eventType.id.eq(id);
  }

  public static BooleanExpression getNotKeyDates() {
    return QEvent.event.eventType.isKeyDate.eq(false);
  }

  public static BooleanExpression getCrossedEvents(LocalDateTime start, LocalDateTime finish,
      Integer roomId) {
    return QEvent.event.start.before(finish).and(QEvent.event.end.after(start))
        .and(QEvent.event.room.id.eq(roomId));
  }
}
