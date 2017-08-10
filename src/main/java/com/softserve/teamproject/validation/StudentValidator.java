package com.softserve.teamproject.validation;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Student;
import java.util.List;

public interface StudentValidator {

  void checkStudentFields(List<Student> students);

  void checkCoordinatorLocationToManipulateStudent(Group group, String userName);

}
