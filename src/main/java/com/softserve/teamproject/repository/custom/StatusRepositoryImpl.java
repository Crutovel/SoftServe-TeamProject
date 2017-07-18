package com.softserve.teamproject.repository.custom;

import static com.softserve.teamproject.repository.expression.StatusExpressions.getByName;

import com.softserve.teamproject.entity.QStatus;
import com.softserve.teamproject.entity.Status;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class StatusRepositoryImpl extends QueryDslRepositorySupport
    implements StatusRepositoryCustom {

  public StatusRepositoryImpl() {
    super(Status.class);
  }

  @Override
  public Status getStatusByName(String name) {
    return from(QStatus.status).where(getByName(name)).fetchFirst();
  }
}
