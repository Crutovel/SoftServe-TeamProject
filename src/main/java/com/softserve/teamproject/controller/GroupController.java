package com.softserve.teamproject.controller;

import com.softserve.teamproject.dto.GroupsFilter;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller provides methods for the interaction with the groups.
 */
@RestController
public class GroupController {

  private TeacherGroupsManipulationService groupsActions;
  private GroupService groupService;

  @Autowired
  public void setTeacherGroupsManipulationService(TeacherGroupsManipulationService groupsActions) {
    this.groupsActions = groupsActions;
  }

  @Autowired
  public void setGroupService(GroupService groupService) {
    this.groupService = groupService;
  }

  /**
   * The method displays all the groups of the current authorized teacher. If a user with another
   * role is trying to access this method, 403 "forbidden" will be displayed.
   *
   * @param principal of the Principal type.
   * @return list of groups of the authorized teacher.
   */
  @GetMapping(value = "/groups/my")
  public List<GroupResource> getTeachersGroups(Principal principal) {
    return groupsActions.getAllGroupResourcesOfTheTeacher(principal.getName());
  }

  /**
   * Get groups from the authenticated user location
   *
   * @param principal authorized user
   * @return groups in user location
   */
  @GetMapping(value = "/groups/mylocation")
  public Set<GroupResource> getGroupsFromUserLocation(Principal principal) {
    return groupService.getGroupResourcesFromUserLocation(principal.getName());
  }

  /**
   * Method displays all the existing groups.
   *
   * @return list of all the existing groups.
   */
  @GetMapping(value = "/groups")
  public List<GroupResource> getAllGroups() {
    return groupService.getAllGroupResources();
  }

  /**
   * Method creates a group received in body in json format. Note: the date format accepted:
   * "yyyy-MM-dd"
   *
   * @param group in json format, which is automatically transformed into the object of the Group
   * type
   * @param principal to get the name of the authenticated user
   */
  @PostMapping(value = "/groups")
  public void createGroup(@RequestBody @Valid Group group, Principal principal) {
    groupService.addGroup(group, principal.getName());
  }

  /**
   * Methods deletes the group by id.
   *
   * @param id is received as a path variable
   * @param principal helps to identify the authenticated user
   */
  @DeleteMapping(value = "/groups/{id}")
  public void deleteGroup(@PathVariable Integer id, Principal principal) {
    groupService.deleteGroup(id, principal.getName());
  }

  /**
   * Method edits current group according to the new values.
   * @param group which is being updated
   * @param id of group which will be updated (for getting a previous group status)
   * @param principal is an authenticated user
   */
  @PutMapping(value = "/groups/{id}")
  public void editGroup(@RequestBody Group group, @PathVariable Integer id, Principal principal) {
    group.setId(id);
    Status currentStatus = groupService.getGroupById(id).getStatus();
    groupService.fieldsCheck(group);
    groupService.updateGroup(group, currentStatus, principal.getName());
  }

  /**
   * Gets group by id.
   *
   * @param id is id of a group we want to find.
   * @return group with entered id.
   */
  @GetMapping(value = "/groups/{id}")
  public Group getGroupById(@PathVariable Integer id) {
    return groupService.getGroupById(id);
  }

  /**
   * Get groups by filter.
   *
   * @param requestFilter group dto
   * @return groups info
   */
  @PostMapping(value = "/groups/filter")
  public Iterable getGroupsByFilter(@RequestBody GroupsFilter requestFilter) {
    return groupService.getGroupsByFilter(requestFilter);
  }
}
