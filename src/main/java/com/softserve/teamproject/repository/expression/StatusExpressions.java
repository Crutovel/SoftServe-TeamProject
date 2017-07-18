package com.softserve.teamproject.repository.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.softserve.teamproject.entity.QStatus;

public class StatusExpressions {

  public static BooleanExpression getByName(String name) {
    return QStatus.status.name.eq(name);
  }
}
