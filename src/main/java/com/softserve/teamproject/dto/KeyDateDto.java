package com.softserve.teamproject.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.deserializer.EventTypeDeserializer;
import com.softserve.teamproject.entity.deserializer.GroupDeserializer;
import com.softserve.teamproject.entity.deserializer.LocalDateDeserializer;
import com.softserve.teamproject.validation.KeyDates;
import java.time.LocalDate;

@KeyDates
public class KeyDateDto {

  private Integer id;
  @JsonDeserialize(using = GroupDeserializer.class)
  private Group group;
  @JsonDeserialize(using = EventTypeDeserializer.class)
  private EventType eventType;
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate date;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public Event toEntity() {
    return new Event(id, date.atTime(0, 0), 0, null, group, eventType);
  }


  @Override
  public String toString() {
    return "{" +
        "group:" + (group != null ? group.getName() : null) +
        ", eventType:" + eventType.getName() +
        ", date:" + date +
        '}';
  }
}
