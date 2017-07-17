package com.softserve.teamproject.repository.custom;


import static com.softserve.teamproject.repository.expression.GroupExpressions.getByLocationIds;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.QGroup;
import java.util.List;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class GroupRepositoryImpl extends QueryDslRepositorySupport implements
    GroupRepositoryCustom {

  public GroupRepositoryImpl() {
    super(Group.class);
  }

  public List<Group> getGroupsByLocationIds(Integer[] locations) {
    return from(QGroup.group).where(getByLocationIds(locations)).fetch();
  }


}
