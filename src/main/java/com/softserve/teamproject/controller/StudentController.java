package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.resource.StudentResource;
import com.softserve.teamproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle students.
 */
@RestController
public class StudentController {

  private StudentService studentService;

  @Autowired
  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

  /**
   * Get students by groupId.
   *
   * @param groupId is received as a request param
   * @return students info by groupId or just all students
   */
  @GetMapping(value = "/students")
  public Iterable<StudentResource> getStudents(
      @RequestParam(value = "groupid", required = false) Integer groupId) {
    if (groupId != null) {
      return studentService.getStudentsByGroupId(groupId);
    }
    return studentService.getAllStudents();
  }
}
