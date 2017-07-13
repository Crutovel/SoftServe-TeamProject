package com.softserve.temproject.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.softserve.teamproject.controller.LoginController;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoginControllerTest {

  @Mock
  HttpServletResponse response;


  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }


  @Test
  public void notAllowedMethod_GetRequest_NotAllowedReturned() throws IOException {
    ResponseEntity entity = new LoginController().notAllowedMethod(response);

    verify(
        response, times(1)).
        sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Not allowed");
    Assert.assertEquals(entity.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
  }
}
