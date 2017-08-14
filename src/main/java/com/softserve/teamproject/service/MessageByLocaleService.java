package com.softserve.teamproject.service;

/**
 * Use for localize messages for user
 */
public interface MessageByLocaleService {

  public String getMessage(String id);

  public String getMessage(String id, Object... args);
}
