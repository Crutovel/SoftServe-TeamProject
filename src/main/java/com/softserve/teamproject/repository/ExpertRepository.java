package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ExpertRepository extends JpaRepository<Expert, Integer>,
    QueryDslPredicateExecutor {

  Expert findByExpertName(String name);

}
