package com.softserve.teamproject.repository.expression;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.softserve.teamproject.entity.QUser;

public class UserExpressions {
  public static BooleanExpression getByNickName(String nickName) {
    return QUser.user.nickName.eq(nickName);
  }
}
