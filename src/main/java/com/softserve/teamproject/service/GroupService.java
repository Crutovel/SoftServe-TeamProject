package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

public interface GroupService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  List<Group> getGroupsByLocationIds(Integer[] locations);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  List<Group> getAllGroups();

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  void addGroup(Group group, String userName) throws AccessDeniedException;

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  void deleteGroup(int groupId, String userName) throws AccessDeniedException;

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
  void updateGroup(Group group, Status currentStatus, String userName) throws AccessDeniedException;

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
  Group getGroupById(Integer id);

  boolean isValid(Group group);
}
