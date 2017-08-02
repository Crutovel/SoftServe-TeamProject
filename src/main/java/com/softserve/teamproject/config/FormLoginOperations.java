package com.softserve.teamproject.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Multimap;

import springfox.documentation.builders.ApiListingBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiListing;
import springfox.documentation.service.Operation;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiListingScanningContext;
import springfox.documentation.spring.web.scanners.ApiModelReader;

public class FormLoginOperations extends ApiListingScanner {

  @Autowired
  private TypeResolver typeResolver;

  @Autowired
  public FormLoginOperations(ApiDescriptionReader apiDescriptionReader,
      ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
    super(apiDescriptionReader, apiModelReader, pluginsManager);
  }

  @Override
  public Multimap<String, ApiListing> scan(ApiListingScanningContext context) {

    final Multimap<String, ApiListing> def = super.scan(context);
    final List<ApiDescription> apis = new LinkedList<>();
    final List<Operation> operations = new ArrayList<>();
    final List<Operation> operationsLogout = new ArrayList<>();

    operations.add(new OperationBuilder(new CachingOperationNameGenerator())
        .method(HttpMethod.POST)
        .uniqueId("login")
        .parameters(Collections.singletonList(new ParameterBuilder()
            .name("body")
            .required(true)
            .description("User's credentials in JSON format. Example: {\n"
                + "  \"username\": \"DmytroPetin\",\n"
                + "  \"password\": \"fgdfg24sd\"\n"
                + "}")
            .parameterType("body")
            .type(typeResolver.resolve(String.class))
            .modelRef(new ModelRef("JSON"))
            .build()))
        .summary("Log in")
        .notes("Here you can log in")
        .build());

    operationsLogout.add(new OperationBuilder(new CachingOperationNameGenerator())
        .method(HttpMethod.GET)
        .uniqueId("logout")
        .summary("Log out")
        .notes("Here you can log out")
        .build());

    apis.add(new ApiDescription("/login", "Authentication documentation",
        operations, false));

    apis.add(new ApiDescription("/logout", "Authentication documentation",
        operationsLogout, false));

    def.put("authentication",
        new ApiListingBuilder(context.getDocumentationContext().getApiDescriptionOrdering())
            .apis(apis)
            .description("Custom authentication")
            .build());

    return def;
  }
}
