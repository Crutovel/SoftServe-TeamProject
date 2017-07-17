package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class is an implementation of TeacherGroupsManipulationService interface; it provides the
 * methods needed to specify the actions that a teacher can perform with the groups.
 */
@Service
public class TeacherGroupsManipulationServiceImpl implements TeacherGroupsManipulationService {

  private GroupRepository groupRepository;

  @Autowired
  public void setGroupRepository(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  @Override
  public List<Group> getAllGroupsOfTheTeacher(String teachersName) {
    return groupRepository.getAllGroupsOfTheTeacher(teachersName);
  }
}
