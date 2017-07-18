package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Group;
import java.util.List;
import com.softserve.teamproject.repository.custom.GroupRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Integer>,
    QueryDslPredicateExecutor, GroupRepositoryCustom {

  /**
   * The method uses JPQl customized query in the annotation to retrieve all the users of the
   * particular teacher from the database.
   */
  @Query("SELECT g FROM Group g JOIN g.teacher t WHERE t.nickName = LOWER(:teachersName)")
  List<Group> getAllGroupsOfTheTeacher(@Param("teachersName") String teachersName);

  Group findByName(String name);
}
