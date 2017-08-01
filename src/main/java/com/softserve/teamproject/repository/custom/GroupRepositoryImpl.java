package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.QGroup;
import java.util.List;
import static com.softserve.teamproject.repository.expression.GroupExpressions.getUndeleted;
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

}
