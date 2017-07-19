package com.softserve.teamproject.controller;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String MSG_NOT_FOUND = "Not Found";
  private static final String MSG_BAD_REQUEST = "Bad request";
  private static final String MSG_ACCESS_DENIED = "Access Denied";

  public RestResponseEntityExceptionHandler() {
    super();
  }

  @SuppressWarnings("unchecked")
  private JSONObject createResponseBody(String message) {
    JSONObject body = new JSONObject();
    body.put("message", message);
    return body;
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handleAccessDeniedException(final Exception ex,
      final WebRequest request) {

    return new ResponseEntity<>(createResponseBody(MSG_ACCESS_DENIED), new HttpHeaders(),
        HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {EntityNotFoundException.class})
  protected ResponseEntity<Object> handleNotFound(final RuntimeException ex,
      final WebRequest request) {

    return handleExceptionInternal(ex, createResponseBody(MSG_NOT_FOUND), new HttpHeaders(),
        HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(value = {ValidationException.class})
  protected ResponseEntity<Object> handleValidationException(
      final RuntimeException ex, final WebRequest request) {
    return handleExceptionInternal(
        ex, createResponseBody(MSG_BAD_REQUEST), new HttpHeaders(),HttpStatus.BAD_REQUEST, request);
  }

}
