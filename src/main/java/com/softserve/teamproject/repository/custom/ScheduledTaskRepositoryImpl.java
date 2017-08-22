package com.softserve.teamproject.repository.custom;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.softserve.teamproject.repository.expression.ScheduledTaskExpression.getTasksBeforeTomorrow;
import static com.softserve.teamproject.repository.expression.ScheduledTaskExpression.isEqualToGroup;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.QScheduledTask;
import com.softserve.teamproject.entity.ScheduledTask;
import java.util.List;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class ScheduledTaskRepositoryImpl extends QueryDslRepositorySupport implements
    ScheduledTaskRepositoryCustom {

  public ScheduledTaskRepositoryImpl() {
    super(ScheduledTask.class);
  }


  /*
    select
        ts1.id,
        ts1.group_id,
        ts1.status_id,
        ts1.time
    from task_scheduler as ts1
    where ts1.status_id = (
	      select
            max(ts2.status_id)
		    from task_scheduler as ts2
        where ts1.group_id=ts2.group_id
        group by ts2.group_id
    )
    and ts1.time < ?
   */
  @Override
  public List<ScheduledTask> getActualTasks() {
    QScheduledTask qScheduledTask = QScheduledTask.scheduledTask;
    QScheduledTask qTask = new QScheduledTask("q");
    return from(qScheduledTask).where(qScheduledTask.updatedStatus.id.eq(
        select(qTask.updatedStatus.id.max())
        .from(qTask).where(qScheduledTask.group.id.eq(qTask.group.id))
        .groupBy(qTask.group.id))
        .and(getTasksBeforeTomorrow()))
        .fetch();
  }

  @Override
  public List<ScheduledTask> getOldTasks() {
    return from(QScheduledTask.scheduledTask).where(getTasksBeforeTomorrow()).fetch();
  }

  @Override
  public List<ScheduledTask> getTasksByGroupId(Group group) {
    return from(QScheduledTask.scheduledTask).where(isEqualToGroup(group)).fetch();
  }
}
