package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.repository.custom.EventRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface EventRepository extends JpaRepository<Event, Integer>,
    QueryDslPredicateExecutor<Event>,EventRepositoryCustom {

}
