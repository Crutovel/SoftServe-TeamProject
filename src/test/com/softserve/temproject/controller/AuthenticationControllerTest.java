package com.softserve.temproject.controller;

import com.softserve.teamproject.controller.AuthenticationController;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public class AuthenticationControllerTest {

  @Mock
  HttpServletRequest request;


  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }


  @Test(expected = HttpRequestMethodNotSupportedException.class)
  public void notAllowedMethod_GetRequest_NotAllowedReturned() throws HttpRequestMethodNotSupportedException {
    new AuthenticationController().loginNotAllowedMethod(request);
  }
}
