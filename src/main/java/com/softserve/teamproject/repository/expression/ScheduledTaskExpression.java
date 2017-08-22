package com.softserve.teamproject.repository.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.QScheduledTask;
import java.time.LocalDate;

public class ScheduledTaskExpression {

  public static BooleanExpression getTasksBeforeTomorrow() {
    return QScheduledTask.scheduledTask.dayOfUpdate.before(LocalDate.now().plusDays(1));
  }

  public static BooleanExpression isEqualToGroup(Group group) {
    return QScheduledTask.scheduledTask.group.eq(group);
  }
}
