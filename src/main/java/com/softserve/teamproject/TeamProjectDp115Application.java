package com.softserve.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EntityScan( basePackageClasses = {TeamProjectDp115Application.class,Jsr310JpaConverters.class})
public class TeamProjectDp115Application {

	public static void main(String[] args) {

		SpringApplication.run(TeamProjectDp115Application.class, args);
	}
}
