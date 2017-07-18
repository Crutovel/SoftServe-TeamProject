package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.security.Principal;
import com.softserve.teamproject.service.GroupService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller provides methods for the interaction with the groups.
 */
@RestController
public class GroupController {

  private TeacherGroupsManipulationService groupsActions;

  @Autowired
  public void setTeacherGroupsManipulationService(TeacherGroupsManipulationService groupsActions) {
    this.groupsActions = groupsActions;
  }

  private GroupService groupService;

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
   * @throws EntityNotFoundException with the message "Not found" if no groups of this teacher were
   * found.
   */
  @RequestMapping(value = "/groups/mygroups", method = RequestMethod.GET)
  @ResponseBody
  public List<Group> getTeachersGroups(Principal principal) {
    List<Group> myGroups = groupsActions.getAllGroupsOfTheTeacher(principal.getName());
    if (myGroups.size() == 0) {
      throw new EntityNotFoundException();
    }
    return myGroups;
  }

  /**
   * Method displays all the existing groups.
   * @return list of all the existing groups.
   */
  @RequestMapping(value = "/groups", method = RequestMethod.GET)
  public List<Group> getAllGroups() {
    return groupService.getAllGroups();
  }
}

