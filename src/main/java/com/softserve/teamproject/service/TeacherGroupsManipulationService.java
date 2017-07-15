package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Group;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

public interface TeacherGroupsManipulationService {

  /**
   * The access to this method is secured and accessible only to the users with the "teacher" role.
   * @param teachersName String value, name of the teacher
   * @return List<Group>: </>List of Groups of this authenticated teacher
   */
  @PreAuthorize("hasAuthority('teacher')")
  List<Group> getAllGroupsOfTheTeacher(String teachersName);

}
