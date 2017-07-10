package com.softserve.teamproject.repository.custom;

import static com.softserve.teamproject.repository.expression.UserExpressions.getByNickName;

import com.softserve.teamproject.entity.QUser;
import com.softserve.teamproject.entity.User;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class UserRepositoryImpl extends QueryDslRepositorySupport implements UserRepositoryCustom {

  public UserRepositoryImpl(Class<?> domainClass) {
    super(domainClass);
  }

  public User getUserByNickName(String nickName) {
    return (User) from(QUser.user).where(getByNickName(nickName));
  }

}
