package com.softserve.teamproject.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.teamproject.entity.serializer.LocalDateSerializer;
import java.time.LocalDate;

public class KeyDateResponseDto {

  private Integer groupId;
  private Integer eventTypeId;
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate date;
  private String errorMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

  public Integer getEventTypeId() {
    return eventTypeId;
  }

  public void setEventTypeId(Integer eventTypeId) {
    this.eventTypeId = eventTypeId;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public KeyDateResponseDto(Integer groupId, Integer eventTypeId, LocalDate date,
      String errorMessage) {
    this.groupId = groupId;
    this.eventTypeId = eventTypeId;
    this.date = date;
    this.errorMessage = errorMessage;
  }
}
