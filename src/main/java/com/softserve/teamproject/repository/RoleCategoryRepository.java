package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.RoleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface RoleCategoryRepository extends JpaRepository<RoleCategory, Integer>,
    QueryDslPredicateExecutor {

}
