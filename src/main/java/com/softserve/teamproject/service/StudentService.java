package com.softserve.teamproject.service;

import com.softserve.teamproject.dto.StudentDto;
import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.resource.StudentResource;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

public interface StudentService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin', 'tes')")
  Iterable<StudentResource> getStudentsByGroupId(Integer groupId);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin','tes')")
  Iterable<StudentResource> getAllStudents();

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin', 'tes')")
  Iterable<StudentResource> addStudents(@Valid List<Student> students, Integer groupId, String userName);

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin', 'tes')")
  Iterable<StudentResource> updateStudents(@Valid List<Student> students, String userName);

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin', 'tes')")
  Student updateSingleStudent(Student student, String userName);

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin', 'tes')")
  StudentResource getStudentResourceById(Integer id);

  @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin', 'tes')")
  StudentResource updateStudent(@Valid Student student);

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin', 'tes')")
  public List<StudentDto> getAllStudentDto();

}
