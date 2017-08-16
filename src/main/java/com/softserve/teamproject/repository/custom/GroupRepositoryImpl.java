package com.softserve.teamproject.repository.custom;

import static com.softserve.teamproject.repository.expression.GroupExpressions.getByLocationId;
import static com.softserve.teamproject.repository.expression.GroupExpressions.getByLocationIds;
import static com.softserve.teamproject.repository.expression.GroupExpressions.getUndeleted;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.QGroup;
import java.util.List;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class GroupRepositoryImpl extends QueryDslRepositorySupport implements
    GroupRepositoryCustom {

  public GroupRepositoryImpl() {
    super(Group.class);
  }

  @Override
  public List<Group> getUndeletedGroups() {
    return from(QGroup.group).where(getUndeleted()).fetch();
  }

  @Override
  public List<Group> getGroupsByLocationId(Integer id) {
    return from(QGroup.group).where(getByLocationId(id)).fetch();
  }

  @Override
  public List<Group> getGroupsByLocationIds(Integer[] ids) {
    return from(QGroup.group).where(getByLocationIds(ids)).fetch();
  }
}
