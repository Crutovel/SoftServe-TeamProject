package com.softserve.teamproject;

import com.softserve.teamproject.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {TeamProjectDp115Application.class,Jsr310JpaConverters.class})
public class TeamProjectDp115Application {

  @Autowired
  SpecializationRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(TeamProjectDp115Application.class, args);
  }
}
