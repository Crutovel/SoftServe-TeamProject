package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.resource.EventResource;
import java.util.List;
import java.util.Map;

public class EventResponseWrapper {

  List<EventResource> succeed;
  Map<KeyDateDto, String> invalid;

  public List<EventResource> getSucceed() {
    return succeed;
  }

  public void setSucceed(List<EventResource> succeed) {
    this.succeed = succeed;
  }

  public Map<KeyDateDto, String> getInvalid() {
    return invalid;
  }

  public void setInvalid(Map<KeyDateDto, String> invalid) {
    this.invalid = invalid;
  }

  public EventResponseWrapper(
      List<EventResource> succeed,
      Map<KeyDateDto, String> invalid) {
    this.succeed = succeed;
    this.invalid = invalid;
  }
}
