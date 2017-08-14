package com.softserve.teamproject.repository.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.sql.SQLExpressions;
import com.softserve.teamproject.entity.QEvent;
import com.softserve.teamproject.entity.QGroup;
import java.time.LocalDateTime;

public class EventExpressions {

  public static BooleanExpression getKeyDates() {
    return QEvent.event.eventType.isKeyDate.eq(true);
  }

  public static BooleanExpression getEventByGroupId(Integer id) {
    return QEvent.event.group.id.eq(id);
  }

  public static BooleanExpression getEventByGroupId(Integer[] groups) {
    return QEvent.event.group.id.in(groups);
  }

  public static BooleanExpression getEventBetweenDates(LocalDateTime start, LocalDateTime finish) {
    return QEvent.event.dateTime.between(start, finish);
  }

  public static BooleanExpression getEventsBeforeStart(LocalDateTime start) {
    return QEvent.event.dateTime.before(start);
  }

  public static BooleanExpression eventByEventTypeId(Integer id) {
    return QEvent.event.eventType.id.eq(id);
  }

  public static BooleanExpression getNotKeyDates() {
    return QEvent.event.eventType.isKeyDate.eq(false);
  }
}
