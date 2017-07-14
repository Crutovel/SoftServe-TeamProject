package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Group;
import java.util.List;

public interface TeacherGroupsManipulationService {

  /**
   * @param teachersName String value, name of the teacher
   * @return List<Group>: </>List of Groups of this authenticated teacher
   */
  List<Group> getAllGroupsOfTheTeacher(String teachersName);

}
