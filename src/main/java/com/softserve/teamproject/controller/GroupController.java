package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.security.Principal;
import com.softserve.teamproject.service.GroupService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  /**
   * Creates new group based on request body parameters. If a current authorized user is
   * coordinator, the group location must equal coordinator location. If a current authorized user
   * is administrator, the group location can be anyone. For other roles creating of group is
   * unavailable.
   *
   * @param group new group from JSON
   * @param principal current authorized user
   */
  @PostMapping(value = "/groups/add")
  public void addGroup(@RequestBody Group group, Principal principal) {
    String userName = principal.getName();
    groupService.addGroup(group, userName);
  }

  /**
   * Deletes group with given id. If a current authorized user is
   * coordinator, the group location must equal coordinator location. If a current authorized user
   * is administrator, the group location can be anyone. For other roles deleting of group is
   * unavailable.
   *
   * @param groupId given group id
   * @param principal current authorized user
   */
  @DeleteMapping(value = "/groups/delete/{id}")
  public void deleteGroup(@PathVariable("id") int groupId, Principal principal) {
    String userName = principal.getName();
    groupService.deleteGroup(groupId, userName);
  }
}

