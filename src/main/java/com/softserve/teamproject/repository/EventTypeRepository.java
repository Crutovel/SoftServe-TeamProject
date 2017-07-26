package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface EventTypeRepository extends JpaRepository<EventType, Integer>,
    QueryDslPredicateExecutor {

}
