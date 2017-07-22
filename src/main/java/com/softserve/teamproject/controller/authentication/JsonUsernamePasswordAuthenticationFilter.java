package com.softserve.teamproject.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonUsernamePasswordAuthenticationFilter extends
    UsernamePasswordAuthenticationFilter {

  private String username;
  private String password;

  @Autowired
  public void setSuccessHandler(
      CustomSavedRequestAwareAuthenticationSuccessHandler successHandler) {
    super.setAuthenticationSuccessHandler(successHandler);
  }

  @Override
  protected String obtainPassword(HttpServletRequest request) {
    String password = null;
    if ("application/json".equals(request.getHeader("Content-Type"))) {
      password = this.password;
    } else {
      password = super.obtainPassword(request);
    }

    return password;
  }

  @Override
  protected String obtainUsername(HttpServletRequest request) {
    String username = null;

    if ("application/json".equals(request.getHeader("Content-Type"))) {
      username = this.username;
    } else {
      username = super.obtainUsername(request);
    }

    return username;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) {

    String s = request.getHeader("Content-Type");
    if ("application/json".equals(request.getHeader("Content-Type"))) {
      try {
        StringBuilder sb = new StringBuilder();
        String line = null;

        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
          sb.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        LoginRequest loginRequest = mapper.readValue(sb.toString(), LoginRequest.class);

        this.username = loginRequest.getUsername();
        this.password = loginRequest.getPassword();
      } catch (Exception e) {
          throw new AuthenticationServiceException("Cannot recognize JSON parameters");/* NOP */
      }
    }
    else {
      throw new AuthenticationServiceException("Content-Type 'application/json' is allowed");
    }
    return super.attemptAuthentication(request, response);
  }
}