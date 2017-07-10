package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface PhoneRepository extends JpaRepository<Phone, Integer>,
    QueryDslPredicateExecutor {

}
