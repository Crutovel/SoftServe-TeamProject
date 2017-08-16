package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.Group;
import java.util.List;

public interface GroupRepositoryCustom {

  List<Group> getUndeletedGroups();

  List<Group> getGroupsByLocationId(Integer id);

  List<Group> getGroupsByLocationIds(Integer[] ids);
}
