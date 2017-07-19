package com.softserve.teamproject.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @RequestMapping(value = "/login",
      method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
  public void loginNotAllowedMethod(HttpServletRequest request)
      throws HttpRequestMethodNotSupportedException {
    String[] allowedMethod = {"POST"};
    throw new HttpRequestMethodNotSupportedException(request.getMethod(), allowedMethod);
  }
}
