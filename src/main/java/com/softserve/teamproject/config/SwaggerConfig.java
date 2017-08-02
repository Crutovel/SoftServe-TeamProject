package com.softserve.teamproject.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiModelReader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig {

  @Bean
  public Docket userApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .groupName("Users")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(userPaths())
        .build();
  }

  @Bean
  public Docket groupApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .groupName("Groups")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(groupPaths())
        .build();
  }

  @Bean
  public Docket locationApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .groupName("Locations")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(locationPaths())
        .build();
  }

  @Bean
  public Docket eventApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .groupName("Events")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(eventPaths())
        .build();
  }

  @Bean
  public Docket studentApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .groupName("Students")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(studentPaths())
        .build();
  }

  private Predicate<String> userPaths() {
    return or(
        regex("/users.*"),
        regex("/user/profile.*"));
  }

  private Predicate<String> groupPaths() {
    return regex("/groups.*");
  }

  private Predicate<String> locationPaths() {
    return regex("/locations.*");
  }

  private Predicate<String> eventPaths() {
    return regex("/events.*");
  }

  private Predicate<String> studentPaths() {
    return regex("/students.*");
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Caesar Project API")
        .description("Project Caesar for SoftServe IT-academy")
        .termsOfServiceUrl("https://github.com/Crutovel/SoftServe-TeamProject")
        .build();
  }

  @Primary
  @Bean
  public ApiListingScanner addExtraOperations(ApiDescriptionReader apiDescriptionReader,
      ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
    return new FormLoginOperations(apiDescriptionReader, apiModelReader, pluginsManager);
  }
}