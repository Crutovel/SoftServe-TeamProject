package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.EnglishLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface EnglishLevelRepository extends JpaRepository<EnglishLevel, Integer>,
    QueryDslPredicateExecutor<EnglishLevel> {

  EnglishLevel findByName(String name);

}
