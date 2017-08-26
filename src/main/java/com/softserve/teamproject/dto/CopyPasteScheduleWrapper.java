package com.softserve.teamproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.deserializer.GroupDeserializer;
import com.softserve.teamproject.entity.serializer.GroupSerializer;
import com.softserve.teamproject.entity.deserializer.LocalDateDeserializer;
import com.softserve.teamproject.entity.serializer.LocalDateSerializer;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;


public class CopyPasteScheduleWrapper {

  @NotNull(message = "{NotNull.copyPasteScheduleWrapper.group}")
  @JsonDeserialize(using = GroupDeserializer.class)
  @JsonSerialize(using = GroupSerializer.class)
  private Group group;

  @NotNull(message = "{NotNull.copyPasteScheduleWrapper.copyWeekDate}")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate copyWeekDate;

  @JsonInclude(Include.NON_NULL)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate pasteWeekDate;

  @JsonInclude(Include.NON_NULL)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate pasteFillDate;

  private Iterable<EventDto> conflicts;

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public LocalDate getCopyWeekDate() {
    return copyWeekDate;
  }

  public void setCopyWeekDate(LocalDate copyWeekDate) {
    this.copyWeekDate = copyWeekDate;
  }

  public LocalDate getPasteWeekDate() {
    return pasteWeekDate;
  }

  public void setPasteWeekDate(LocalDate pasteWeekDate) {
    this.pasteWeekDate = pasteWeekDate;
  }

  public LocalDate getPasteFillDate() {
    return pasteFillDate;
  }

  public void setPasteFillDate(LocalDate pasteFillDate) {
    this.pasteFillDate = pasteFillDate;
  }

  public Iterable<EventDto> getConflicts() {
    return conflicts;
  }

  public void setConflicts(Iterable<EventDto> conflicts) {
    this.conflicts = conflicts;
  }

  @Override
  public String toString() {
    return "CopyPasted Schedule{"
        + ", group='" + group.getName() + '\''
        + ", copyWeekDate='" + copyWeekDate + '\''
        + ", pasteWeekDate='" + pasteWeekDate + '\''
        + ", pasteFillDate='" + pasteFillDate + '\''
        + ", conflicts='" + conflicts.toString() + '\''
        + '}';
  }
}
