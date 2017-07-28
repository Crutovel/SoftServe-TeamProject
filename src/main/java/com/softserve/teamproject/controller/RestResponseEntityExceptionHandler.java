package com.softserve.teamproject.controller;

import java.util.Set;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(final Exception ex,
      final WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handleAccessDeniedException(final Exception ex,
      final WebRequest request) {
    String customMessage = MSG_ACCESS_DENIED + ex.getMessage();
    return new ResponseEntity<>(createResponseBody(customMessage), new HttpHeaders(),
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
        ex, createResponseBody(MSG_BAD_REQUEST + ": " + ex.getMessage()), new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
    if (!CollectionUtils.isEmpty(supportedMethods)) {
      headers.setAllow(supportedMethods);
    }
    return handleExceptionInternal(ex, createResponseBody(ex.getMessage()), headers,
        status, request);
  }

}
