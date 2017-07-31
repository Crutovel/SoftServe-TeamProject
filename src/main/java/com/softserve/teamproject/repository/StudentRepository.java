package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.repository.custom.StudentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface StudentRepository extends JpaRepository<Student, Integer>,
    QueryDslPredicateExecutor<Student>, StudentRepositoryCustom {

}
