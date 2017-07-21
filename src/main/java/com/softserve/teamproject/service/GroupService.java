package com.softserve.teamproject.service;

import com.softserve.teamproject.dto.GroupsFilter;
import com.softserve.teamproject.entity.Group;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

public interface GroupService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  List<Group> getAllGroups();

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  void addGroup(Group group, String userName) throws AccessDeniedException;

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  void deleteGroup(int groupId, String userName) throws AccessDeniedException;

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")

  Iterable<GroupResource> getGroupsByFilter(GroupsFilter filter);

  /**
   * Retrieve from database all groups in current user location
   * @param principalName user nick name
   * @return list of groups in current user location
   */
  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Set<GroupResource> getGroupResourcesFromUserLocation(String principalName);
}
