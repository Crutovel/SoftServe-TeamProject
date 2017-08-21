package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>, QueryDslPredicateExecutor,
    UserRepositoryCustom {
  @Modifying
@Query("delete from User u where u.id=?1")
  public void deleteEntity(int id);
}
