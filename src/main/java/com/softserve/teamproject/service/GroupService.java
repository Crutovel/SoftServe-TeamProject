package com.softserve.teamproject.service;

import com.softserve.teamproject.dto.GroupsFilter;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.resource.GroupResource;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;

public interface GroupService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  List<GroupResource> getAllGroupResources();

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  GroupResource addGroup(Group group, String userName) throws AccessDeniedException;

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  void deleteGroup(int groupId, String userName) throws AccessDeniedException;

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
  GroupResource updateGroup(@Valid Group group, Status currentStatus, String userName) throws AccessDeniedException;

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
  Group getGroupById(Integer id);

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
  GroupResource getGroupResourceById(Integer id);

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
