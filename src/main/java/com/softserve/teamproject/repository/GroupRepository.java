package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.repository.custom.GroupRepositoryCustom;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface GroupRepository extends JpaRepository<Group, Integer>,
    QueryDslPredicateExecutor<Group>, GroupRepositoryCustom {

  /**
   * The method uses property expression feature of the Spring Data JPA to retrieve all the groups of the
   * particular teacher from the database.
   */
  List<Group> findByTeachers_NickName(String teachersName);

  Group findByName(String name);

  @Transactional
  void deleteById(int id);
}
