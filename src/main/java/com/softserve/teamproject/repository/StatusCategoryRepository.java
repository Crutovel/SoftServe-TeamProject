package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.StatusCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface StatusCategoryRepository extends JpaRepository<StatusCategory, Integer>,
    QueryDslPredicateExecutor {

}
