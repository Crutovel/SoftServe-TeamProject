package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface EmailRepository extends JpaRepository<Email, Integer>,
    QueryDslPredicateExecutor {

}
