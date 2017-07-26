package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.resource.GroupResource;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Interface that provides the methods needed to specify the actions that a teacher can perform with
 * the groups.
 */
public interface TeacherGroupsManipulationService {

  /**
   * The access to this method is secured and accessible only to the users with the "teacher" role.
   *
   * @param teachersName String value, name of the teacher
   * @return List<Group>: List of Groups of this authenticated teacher
   */
  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  List<Group> getAllGroupsOfTheTeacher(String teachersName);

  /**
   * The access to this method is secured and accessible only to the users with the "teacher" role.
   *
   * @param teachersName String value, name of the teacher
   * @return List<GroupResource>: List of GroupResources of this authenticated teacher
   */
  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  List<GroupResource> getAllGroupResourcesOfTheTeacher(String teachersName);

}
