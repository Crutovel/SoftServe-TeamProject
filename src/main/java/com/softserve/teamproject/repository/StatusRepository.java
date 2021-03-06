package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.repository.custom.StatusRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface StatusRepository extends JpaRepository<Status, Integer>,
    QueryDslPredicateExecutor, StatusRepositoryCustom {

  Status findByName(String name);
}
