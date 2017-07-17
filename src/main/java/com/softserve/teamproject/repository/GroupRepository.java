package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.repository.custom.GroupRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface GroupRepository extends JpaRepository<Group, Integer>,
    QueryDslPredicateExecutor, GroupRepositoryCustom {

}
