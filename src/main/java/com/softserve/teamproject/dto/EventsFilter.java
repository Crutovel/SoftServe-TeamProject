package com.softserve.teamproject.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softserve.teamproject.entity.deserializer.LocalDateDeserializer;
import java.time.LocalDate;

public class EventsFilter {

  private Integer[] groups;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate startDate;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate endDate;

  public Integer[] getGroups() {
    return groups;
  }

  public void setGroups(Integer[] groups) {
    this.groups = groups;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate finishDate) {
    this.endDate = finishDate;
  }
}
