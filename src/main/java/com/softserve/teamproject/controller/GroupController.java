package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  @RequestMapping(value = "/groups/my", method = RequestMethod.GET)
  public List<Group> getTeachersGroups(Principal principal) {
    return groupsActions.getAllGroupsOfTheTeacher(principal.getName());
  }

  /**
   * Method displays all the existing groups.
   *
   * @return list of all the existing groups.
   */
  @RequestMapping(value = "/groups", method = RequestMethod.GET)
  public List<Group> getAllGroups() {
    return groupService.getAllGroups();
  }

  /**
   * Method creates a group received in body in json format. Note: the date format accepted:
   * "yyyy-MM-dd"
   *
   * @param group in json format, which is automatically transformed into the object of the Group
   * type
   * @param principal to get the name of the authenticated user
   */
  @RequestMapping(value = "/groups", method = RequestMethod.POST)
  public void createGroup(@RequestBody @Valid Group group, Principal principal) {
    groupService.addGroup(group, principal.getName());
  }

  /**
   * Methods deletes the group by id.
   *
   * @param id is received as a path variable
   * @param principal helps to identify the authenticated user
   */
  @RequestMapping(value = "/groups/{id}", method = RequestMethod.DELETE)
  public void deleteGroup(@PathVariable Integer id, Principal principal) {
    groupService.deleteGroup(id, principal.getName());
  }

  /**
   * Method edits current group according to the new values.
   * @param group which is being updated
   * @param id of group which will be updated (for getting a previous group status)
   * @param principal is an authenticated user
   */
  @RequestMapping(value = "/groups/{id}", method = RequestMethod.PUT)
  public void editGroup(@RequestBody @Valid Group group, @PathVariable Integer id, Principal principal) {
    group.setId(id);
    Status currentStatus = groupService.getGroupById(id).getStatus();
    groupService.updateGroup(group, currentStatus, principal.getName());
  }
}
