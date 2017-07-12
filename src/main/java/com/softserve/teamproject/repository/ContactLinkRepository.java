package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.ContactLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface ContactLinkRepository extends JpaRepository<ContactLink, Integer>,
    QueryDslPredicateExecutor {

}
