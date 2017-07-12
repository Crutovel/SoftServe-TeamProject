package com.softserve.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class TeamProjectDp115Application {

  public static void main(String[] args) {
    SpringApplication.run(TeamProjectDp115Application.class, args);
  }
}
