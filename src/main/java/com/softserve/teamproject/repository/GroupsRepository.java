package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface GroupsRepository extends JpaRepository<Groups, Integer>,
    QueryDslPredicateExecutor {

}
