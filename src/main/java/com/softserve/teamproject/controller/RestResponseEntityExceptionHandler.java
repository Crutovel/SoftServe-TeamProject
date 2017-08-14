package com.softserve.teamproject.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String MSG_NOT_FOUND = "Not Found";
  private static final String MSG_BAD_REQUEST = "Bad request";
  private static final String MSG_ACCESS_DENIED = "Access Denied";
  private static final String MSG_WRONG_TYPE = "Wrong type of request parameter";
  private static final String VALIDATION_ERRORS = "validationErrors";

  public RestResponseEntityExceptionHandler() {
    super();
  }

  @SuppressWarnings("unchecked")
  private JSONObject createResponseBody(String message) {
    JSONObject body = new JSONObject();
    body.put("message", message);
    return body;
  }

  @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(final Exception ex,
      final WebRequest request) {
    return new ResponseEntity<>(createResponseBody(ex.getMessage()), new HttpHeaders(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handleAccessDeniedException(final Exception ex,
      final WebRequest request) {
    String customMessage = MSG_ACCESS_DENIED + ": " + ex.getMessage();
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

  @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
      final RuntimeException ex, final WebRequest request) {
    return handleExceptionInternal(
        ex, createResponseBody(MSG_WRONG_TYPE), new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class})
  protected ResponseEntity<Object> handleConstraintViolationException(
      final ConstraintViolationException ex, final WebRequest request) {
    Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

    Set<String> messages = new HashSet<>(constraintViolations.size());
    messages.addAll(constraintViolations.stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.toList()));
    return handleExceptionInternal(
        ex, createResponseBody(MSG_BAD_REQUEST + ": " + messages), new HttpHeaders(),
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

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    ModelMap map = new ModelMap();
    ModelMap errorMap = new ModelMap();
    for (FieldError fieldError : errors) {
      errorMap.addAttribute(fieldError.getField(), fieldError.getDefaultMessage());
    }
    map.addAttribute(VALIDATION_ERRORS, errorMap);

    return handleExceptionInternal(
        ex, createResponseBody(MSG_BAD_REQUEST + ": " + map.toString()), new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }
}
