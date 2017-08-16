package com.softserve.teamproject.validation.impl;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.StudentRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.validation.StudentValidator;
import java.lang.reflect.Field;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class StudentValidatorImpl implements StudentValidator {

  private StudentRepository studentRepository;
  private UserRepository userRepository;

  @Autowired
  public void setStudentRepository(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void fillNotUpdatedFields(List<Student> students) {
    for (Student student : students) {
      fillNotUpdatedFields(student);
    }
  }

  @Override
  public void fillNotUpdatedFields(Student student) {
    Student existedStudent = studentRepository.findOne(student.getId());
    Class<?> studentClass = student.getClass();
    for (Field field : studentClass.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (field.get(student) == null) {
          Field existedField = existedStudent.getClass().getDeclaredField(field.getName());
          existedField.setAccessible(true);
          field.set(student, existedField.get(existedStudent));
        }
      } catch (IllegalAccessException | NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void checkCoordinatorLocationToManipulateStudent(Group group, String userName) {
    User user = userRepository.getUserByNickName(userName);
    if (user.getRole().getName().equals("coordinator") && !user.getLocation()
        .equals(group.getLocation())) {
      throw new AccessDeniedException("Coordinator can't add/edit students in the alien location.");
    }
  }

  @Override
  public void checkPresentImageAndCvNames(Student student) {
    if (student.getImage() != null && student.getImageName() == null) {
      throw new IllegalArgumentException("You must send image name with image");
    }

    if (student.getCv() != null && student.getCvName() == null) {
      throw new IllegalArgumentException("You must send CV name with CV");
    }
  }
}
