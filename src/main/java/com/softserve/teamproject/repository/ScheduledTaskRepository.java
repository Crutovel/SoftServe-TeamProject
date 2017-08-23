package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.ScheduledTask;
import com.softserve.teamproject.repository.custom.ScheduledTaskRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, Integer>,
    QueryDslPredicateExecutor<ScheduledTask>, ScheduledTaskRepositoryCustom {

}
