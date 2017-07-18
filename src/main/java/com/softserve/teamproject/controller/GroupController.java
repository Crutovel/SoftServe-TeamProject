package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.security.Principal;
import com.softserve.teamproject.service.GroupService;
import java.util.List;
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
 * The controller provides methods for teacher's interaction with the groups.
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
   */
  @RequestMapping(value = "/teacher/groups", method = RequestMethod.GET)
  @ResponseBody
  public List<Group> getTeachersGroups(Principal principal) {
    return groupsActions.getAllGroupsOfTheTeacher(principal.getName());
  }

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
   * @param group new group
   * @param principal current authorized user
   * @return the result - OK or BAD_REQUEST
   */
  @PostMapping(value = "/groups/add")
  public ResponseEntity addGroup(@RequestBody Group group, Principal principal) {
    String userName = principal.getName();
    if (groupService.addGroup(group, userName)) {
      return new ResponseEntity(HttpStatus.OK);
    }

    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }

  /**
   * Deletes group with given id. If a current authorized user is
   * coordinator, the group location must equal coordinator location. If a current authorized user
   * is administrator, the group location can be anyone. For other roles deleting of group is
   * unavailable.
   *
   * @param groupId given group id
   * @param principal current authorized user
   * @return the result - OK or BAD_REQUEST
   */
  @DeleteMapping(value = "/groups/delete/{id}")
  public ResponseEntity deleteGroup(@PathVariable("id") int groupId, Principal principal) {
    String userName = principal.getName();
    if (groupService.deleteGroup(groupId, userName)) {
      return new ResponseEntity(HttpStatus.OK);
    }

    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }
}

