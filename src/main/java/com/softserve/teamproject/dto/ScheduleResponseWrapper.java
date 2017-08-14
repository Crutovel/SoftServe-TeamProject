package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.resource.EventResource;
import java.util.List;
import java.util.Map;

public class ScheduleResponseWrapper {

  List<EventResource> succeed;
  Map<String, String> invalid;

  public List<EventResource> getSucceed() {
    return succeed;
  }

  public void setSucceed(List<EventResource> succeed) {
    this.succeed = succeed;
  }

  public Map<String, String> getInvalid() {
    return invalid;
  }

  public void setInvalid(Map<String, String> invalid) {
    this.invalid = invalid;
  }

  public ScheduleResponseWrapper(
      List<EventResource> succeed,
      Map<String, String> invalid) {
    this.succeed = succeed;
    this.invalid = invalid;
  }
}
