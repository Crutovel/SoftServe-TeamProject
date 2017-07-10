package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface CountryRepository extends JpaRepository<Country, Integer>,
    QueryDslPredicateExecutor {

}
