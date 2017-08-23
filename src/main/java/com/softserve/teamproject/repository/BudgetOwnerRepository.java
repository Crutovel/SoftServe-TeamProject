package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.BudgetOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface BudgetOwnerRepository extends JpaRepository<BudgetOwner, Integer>,
    QueryDslPredicateExecutor {

  BudgetOwner findByName(String name);
}
