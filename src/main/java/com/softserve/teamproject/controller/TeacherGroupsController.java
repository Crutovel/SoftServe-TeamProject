package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeacherGroupsController {

  private TeacherGroupsManipulationService groupsActions;

  @Autowired
  public void setTeacherGroupsManipulationService(TeacherGroupsManipulationService groupsActions) {
    this.groupsActions = groupsActions;
  }

  @RequestMapping(value = "/teacher/groups", method = RequestMethod.GET)
  @ResponseBody
  public List<Group> getTeachersGroups(Principal principal) {
    return groupsActions.getAllGroupsOfTheTeacher(principal.getName());
  }

}