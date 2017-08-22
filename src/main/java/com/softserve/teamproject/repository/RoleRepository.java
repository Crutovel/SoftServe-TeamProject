package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface RoleRepository extends JpaRepository<Role, Integer>,
    QueryDslPredicateExecutor {

  public Role findByName(String name);
}
