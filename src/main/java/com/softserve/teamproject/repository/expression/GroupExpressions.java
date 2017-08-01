package com.softserve.teamproject.repository.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.softserve.teamproject.entity.QGroup;

public class GroupExpressions {

  public static BooleanExpression getByLocationIds(Integer[] locations) {
    return QGroup.group.location.id.in(locations);
  }

  public static BooleanExpression getUndeleted() {
    return QGroup.group.isDeleted.isFalse();
  }
}
