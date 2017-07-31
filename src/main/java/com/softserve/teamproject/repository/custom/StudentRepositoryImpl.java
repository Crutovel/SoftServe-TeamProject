package com.softserve.teamproject.repository.custom;

import static com.softserve.teamproject.repository.expression.StudentExpressions.getByGroupId;

import com.softserve.teamproject.entity.QStudent;
import com.softserve.teamproject.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class StudentRepositoryImpl extends QueryDslRepositorySupport implements
    StudentRepositoryCustom {

  public StudentRepositoryImpl() {
    super(Student.class);
  }

  public List<Student> getStudentsByGroupId(Integer groupId) {
    return from(QStudent.student).where(getByGroupId(groupId)).fetch();
  }
}
