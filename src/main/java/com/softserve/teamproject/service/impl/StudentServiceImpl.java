package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.assembler.StudentResourceAssembler;
import com.softserve.teamproject.entity.resource.StudentResource;
import com.softserve.teamproject.repository.StudentRepository;
import com.softserve.teamproject.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

  private StudentRepository studentRepository;

  @Autowired
  public void setStudentRepository(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  private StudentResourceAssembler studentResourceAssembler;

  @Autowired
  public void setStudentResourceAssembler(
      StudentResourceAssembler studentResourceAssembler) {
    this.studentResourceAssembler = studentResourceAssembler;
  }

  /**
   * Gets students by group id.
   *
   * @param groupId is id of a group
   * @return students by group id
   */
  public Iterable<StudentResource> getStudentsByGroupId(Integer groupId) {
    return convertToResource(studentRepository.getStudentsByGroupId(groupId));
  }

  /**
   * Gets all students.
   *
   * @return all students
   */
  public Iterable<StudentResource> getAllStudents() {
    return convertToResource(studentRepository.findAll());
  }

  private Iterable<StudentResource> convertToResource(Iterable<Student> students) {
    List<StudentResource> studentResources = new ArrayList<>();
    students.forEach(student -> studentResources.add(studentResourceAssembler.toResource(student)));
    return studentResources;
  }
}
