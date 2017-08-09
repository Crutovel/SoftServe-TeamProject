package com.softserve.teamproject.validation;

import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.User;
import java.util.List;

public interface StudentValidator {

  void checkStudentFields(List<Student> students);

  void checkCoordinatorLocationToManipulateStudent(Student student, String userName);

}
