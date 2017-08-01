package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.resource.StudentResource;
import org.springframework.security.access.prepost.PreAuthorize;

public interface StudentService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<StudentResource> getStudentsByGroupId(Integer groupId);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<StudentResource> getAllStudents();
}
