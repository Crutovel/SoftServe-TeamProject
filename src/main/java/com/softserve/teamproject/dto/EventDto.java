package com.softserve.teamproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.entity.serializer.EventTypeSerializer;
import com.softserve.teamproject.entity.serializer.LocalDateTimeSerializer;
import com.softserve.teamproject.entity.serializer.RoomSerializer;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;


public class EventDto implements Comparable<EventDto> {

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDateTime start;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDateTime end;

  @JsonSerialize(using = RoomSerializer.class)
  private Room room;

  @JsonSerialize(using = EventTypeSerializer.class)
  private EventType eventType;

  @JsonInclude(Include.NON_NULL)
  @JsonProperty(access = Access.READ_ONLY)
  private String message;

  public EventDto() {

  }

  public EventDto(Event event) {
    this.start = event.getStart();
    this.end = event.getEnd();
    this.room = event.getRoom();
    this.eventType = event.getEventType();
  }

  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null) {
      return false;
    }
    if (getClass() != otherObject.getClass()) {
      return false;
    }
    EventDto other = (EventDto) otherObject;
    return Objects.equals(start, other.start) && Objects.equals(end, other.end)
        && Objects.equals(eventType, other.eventType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, eventType);
  }

  @Override
  public int compareTo(EventDto event) {
    if (this.start.isEqual(event.getStart())) {
      return 0;
    } else {
      if (this.start.isAfter(event.getStart())) {
        return 1;
      } else {
        return -1;
      }
    }
  }

  @Override
  public String toString() {
    return "Event{"
        + ", start='" + start + '\''
        + ", end='" + end + '\''
        + ", room='" + room.getNumber() + '\''
        + ", eventType='" + eventType.getName() + '\''
        + '}';
  }
}
