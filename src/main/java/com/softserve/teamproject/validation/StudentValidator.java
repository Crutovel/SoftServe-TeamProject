package com.softserve.teamproject.validation;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Student;
import java.util.List;

public interface StudentValidator {

  void fillNotUpdatedFields(List<Student> students);

  void fillNotUpdatedFields(Student student);

  void checkCoordinatorLocationToManipulateStudent(Group group, String userName);

  void checkPresentImageAndCvNames(Student student);

}
