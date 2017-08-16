package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.resource.StudentResource;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

public interface StudentService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<StudentResource> getStudentsByGroupId(Integer groupId);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<StudentResource> getAllStudents();

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  Iterable<StudentResource> addStudents(@Valid List<Student> students, Integer groupId, String userName);

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  Iterable<StudentResource> updateStudents(@Valid List<Student> students, String userName);

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  Student updateSingleStudent(Student student, String userName);

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
  StudentResource getStudentResourceById(Integer id);

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
  StudentResource updateStudent(Student student);
}
