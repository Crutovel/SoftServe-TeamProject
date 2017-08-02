package com.softserve.teamproject.repository.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.softserve.teamproject.entity.QStudent;

public class StudentExpressions {

  public static BooleanExpression getByGroupId(Integer id) {
    return QStudent.student.group.id.eq(id);
  }
}
