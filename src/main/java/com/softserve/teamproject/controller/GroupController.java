package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
  
  @RequestMapping(value = "/groups", method = RequestMethod.POST)
  public void createGroup(@Valid Group group, Principal principal) {
    groupService.addGroup(group, principal.getName());
  }

  @RequestMapping(value = "/groups/{id}", method = RequestMethod.DELETE)
  public void deleteGroup(@PathVariable Integer id, Principal principal) {
    groupService.deleteGroup(id, principal.getName());
  }
}
