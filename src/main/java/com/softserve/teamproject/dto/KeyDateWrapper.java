package com.softserve.teamproject.dto;

import java.util.List;
import javax.validation.Valid;

public class KeyDateWrapper {

  @Valid
  private List<KeyDateDto> dates;

  public List<KeyDateDto> getDates() {
    return dates;
  }

  public void setDates(List<KeyDateDto> dates) {
    this.dates = dates;
  }
}
