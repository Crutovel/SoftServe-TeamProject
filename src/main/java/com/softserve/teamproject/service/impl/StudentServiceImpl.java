package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.entity.assembler.StudentResourceAssembler;
import com.softserve.teamproject.entity.resource.StudentResource;
import com.softserve.teamproject.repository.EnglishLevelRepository;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.StudentRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.StudentService;
import com.softserve.teamproject.validation.StudentValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class StudentServiceImpl implements StudentService {

  private StudentRepository studentRepository;
  private UserRepository userRepository;
  private StudentResourceAssembler studentResourceAssembler;
  private GroupRepository groupRepository;
  private StudentValidator studentValidator;
  private EnglishLevelRepository englishLevelRepository;

  @Autowired
  public void setStudentRepository(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setStudentResourceAssembler(
      StudentResourceAssembler studentResourceAssembler) {
    this.studentResourceAssembler = studentResourceAssembler;
  }

  @Autowired
  public void setGroupRepository(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  @Autowired
  public void setStudentValidator(StudentValidator studentValidator) {
    this.studentValidator = studentValidator;
  }

  @Autowired
  public void setEnglishLevelRepository(EnglishLevelRepository englishLevelRepository) {
    this.englishLevelRepository = englishLevelRepository;
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

  /**
   * Adds students to the given group.
   *
   * @param students we have to add
   * @param groupId is groups's id in which we have to add students
   * @param userName nickname of current user
   * @return student list on HATEOAS style
   */
  @Override
  public Iterable<StudentResource> addStudents(List<Student> students, Integer groupId,
      String userName) {
    Group group = groupRepository.findOne(groupId);
    studentValidator.checkCoordinatorLocationToManipulateStudent(group, userName);
    for (Student student : students) {
      student.setGroup(group);
      if (student.getEnglishLevel() == null) {
        student.setEnglishLevel(englishLevelRepository.findByName("intermediate"));
      }
    }
    studentRepository.save(students);
    return students.stream().map(studentResourceAssembler::toResource).collect(Collectors.toList());
  }

  /**
   * Updates students.
   *
   * @param students we have to update
   * @param userName nickname of current user
   * @return updated students in HATEOAS style
   */
  @Override
  public Iterable<StudentResource> updateStudents(List<Student> students, String userName) {
    List<StudentResource> studentResourceList = new ArrayList<>();
    for (Student student : students) {
      updateSingleStudent(student, userName);
      StudentResource studentResource = studentResourceAssembler.toResource(student);
      studentResourceList.add(studentResource);
    }
    return studentResourceList;
  }

  /**
   * Updates single student.
   *
   * @param student we have to update
   * @param userName nickname of current user
   * @return updated student
   */
  @Override
  public Student updateSingleStudent(Student student, String userName) {
    Group group = studentRepository.findOne(student.getId()).getGroup();
    studentValidator.checkCoordinatorLocationToManipulateStudent(group, userName);
    studentRepository.save(student);
    return student;
  }

}
