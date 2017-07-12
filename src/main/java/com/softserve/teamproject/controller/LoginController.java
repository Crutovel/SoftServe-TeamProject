package com.softserve.teamproject.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ResponseEntity notAllowedMethod(HttpServletResponse response) {
    try {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Not allowed");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
  }
}
