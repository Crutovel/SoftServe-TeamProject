package com.softserve.teamproject;

import javax.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
@EntityScan(basePackageClasses = {TeamProjectDp115Application.class, Jsr310JpaConverters.class})
public class TeamProjectDp115Application {

  public static void main(String[] args) {
    SpringApplication.run(TeamProjectDp115Application.class, args);
  }

  /**
   * Need Spring validator instead of hibernate validator for custom constraint annotations.
   */
  @Bean
  public Validator validator() {
    return new LocalValidatorFactoryBean();
  }

  /**
   * Set MethodValidationPostProcessor with Spring validator for custom constraint annotations.
   */
  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
    methodValidationPostProcessor.setValidator(validator());
    return methodValidationPostProcessor;
  }
}
