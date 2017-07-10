package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

interface LocationRepository extends JpaRepository<Location, Integer>,
    QueryDslPredicateExecutor {

}
