package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.resource.EventResource;
import java.util.List;

public class ScheduleResponseWrapper {

  private List<EventResource> succeed;
  private List<EventDto> invalid;

  public List<EventResource> getSucceed() {
    return succeed;
  }

  public void setSucceed(List<EventResource> succeed) {
    this.succeed = succeed;
  }

  public  List<EventDto> getInvalid() {
    return invalid;
  }

  public void setInvalid(List<EventDto> invalid) {
    this.invalid = invalid;
  }

  public ScheduleResponseWrapper(){

  }

  public ScheduleResponseWrapper(
      List<EventResource> succeed,
      List<EventDto> invalid) {
    this.succeed = succeed;
    this.invalid = invalid;
  }
}
