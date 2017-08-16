package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.resource.StudentResource;
import com.softserve.teamproject.service.StudentService;
import com.softserve.teamproject.validation.StudentValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle students.
 */
@RestController
@Api(value = "studentController", description = "Operations with students")
public class StudentController {

  private StudentService studentService;
  private StudentValidator studentValidator;

  @Autowired
  public void setStudentService(StudentService studentService) {
    this.studentService = studentService;
  }

  @Autowired
  public void setStudentValidator(StudentValidator studentValidator) {
    this.studentValidator = studentValidator;
  }


  /**
   * Get students by groupId.
   *
   * @param groupId is received as a request param
   * @return students info by groupId or just all students
   */
  @GetMapping(value = "/students", produces = "application/json")
  @ApiOperation(value = "Get students for given group", response = Student.class,
      responseContainer = "List")
  public Iterable<StudentResource> getStudents(
      @RequestParam(value = "groupid", required = false) Integer groupId) {
    if (groupId != null) {
      return studentService.getStudentsByGroupId(groupId);
    }
    return studentService.getAllStudents();
  }

  @PostMapping(value = "/groups/{id}/students", produces = "application/json")
  @ApiOperation(value = "Add students for given group", response = Student.class,
      responseContainer = "List")
  public Iterable<StudentResource> addStudents(@RequestBody List<Student> students,
      @PathVariable Integer id, Principal principal) {
    return studentService.addStudents(students, id, principal.getName());
  }

  @PutMapping(value = "/students", produces = "application/json")
  @ApiOperation(value = "Edit students for given group", response = Student.class,
      responseContainer = "List")
  public Iterable<StudentResource> editStudents(@RequestBody List<Student> students,
      Principal principal) {
    studentValidator.fillNotUpdatedFields(students);
    return studentService.updateStudents(students, principal.getName());
  }

  /**
   * Gets student by given id
   *
   * @param id given student's id
   * @return StudentResource object for this student
   */
  @GetMapping(value = "/students/{id}")
  @ApiOperation(value = "Get student by given id", response = Student.class)
  public StudentResource getStudentById(@PathVariable Integer id) {
    return studentService.getStudentResourceById(id);
  }

  /**
   * Updates given student
   *
   * @param student student with new data
   * @param id id of given student
   * @return StudentResource object for updated student
   */
  @PutMapping(value = "/students/{id}", produces = "application/json")
  @ApiOperation(value = "Update given student", response = Student.class)
  public StudentResource editStudent(@RequestBody Student student, @PathVariable Integer id) {
    student.setId(id);
    studentValidator.checkPresentImageAndCvNames(student);
    studentValidator.fillNotUpdatedFields(student);
    return studentService.updateStudent(student);
  }
}
