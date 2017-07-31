package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.Student;
import java.util.List;

public interface StudentRepositoryCustom {

  List<Student> getStudentsByGroupId(Integer groupId);
}
