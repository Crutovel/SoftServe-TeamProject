package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.repository.expression.GroupExpressions.getByLocationIds;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  private GroupRepository groupRep;

  @Autowired
  public void setGroupRepository(GroupRepository groupRep) {
    this.groupRep = groupRep;
  }

  public Iterable getGroupsByLocationIds(Integer[] locationIds) {
    return groupRep.getGroupsByLocationIds(locationIds);
  }

  public List<Group> getAllGroups() {
    return groupRep.findAll();
  }
}
