package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.resource.EventResource;
import java.util.List;

public class EventResponseWrapper {

  private List<EventResource> succeed;
  private List<KeyDateResponseDto> invalid;

  public List<EventResource> getSucceed() {
    return succeed;
  }

  public void setSucceed(List<EventResource> succeed) {
    this.succeed = succeed;
  }

  public List<KeyDateResponseDto> getInvalid() {
    return invalid;
  }

  public void setInvalid(List<KeyDateResponseDto> invalid) {
    this.invalid = invalid;
  }

  public EventResponseWrapper(List<EventResource> succeed, List<KeyDateResponseDto> invalid) {
    this.succeed = succeed;
    this.invalid = invalid;
  }
}
