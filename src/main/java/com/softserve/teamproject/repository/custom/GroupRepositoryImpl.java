package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.Group;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class GroupRepositoryImpl extends QueryDslRepositorySupport implements
    GroupRepositoryCustom {

  public GroupRepositoryImpl() {
    super(Group.class);
  }

}
