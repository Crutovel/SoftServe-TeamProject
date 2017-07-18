package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Group;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

public interface GroupService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  List<Group> getGroupsByLocationIds(Integer[] locations);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  List<Group> getAllGroups();

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  boolean addGroup(Group group, String userName);

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  boolean deleteGroup(int groupId, String userName);
}
