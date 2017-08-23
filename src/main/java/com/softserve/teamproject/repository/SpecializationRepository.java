package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer>,
    QueryDslPredicateExecutor {

  Specialization findByName(String name);
}
