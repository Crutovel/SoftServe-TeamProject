package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.Group;
import java.util.List;

public interface GroupRepositoryCustom {

  List<Group> getGroupsByLocationIds(Integer[] locations);
}
