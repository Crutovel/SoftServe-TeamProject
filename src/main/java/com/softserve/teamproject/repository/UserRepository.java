package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, QueryDslPredicateExecutor,
    UserRepositoryCustom {

}
