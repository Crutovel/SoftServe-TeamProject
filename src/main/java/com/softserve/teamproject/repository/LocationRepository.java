package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface LocationRepository extends JpaRepository<Location, Integer>,
    QueryDslPredicateExecutor {

  public Location findByName(String name);
}
