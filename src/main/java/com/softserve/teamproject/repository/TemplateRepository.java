package com.softserve.teamproject.repository;

import com.softserve.teamproject.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TemplateRepository extends JpaRepository<Template, Integer>,
    QueryDslPredicateExecutor<Template> {

}
