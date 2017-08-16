package com.softserve.teamproject.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.entity.deserializer.EventTypeSerializer;
import com.softserve.teamproject.entity.serializer.LocalDateTimeSerializer;
import com.softserve.teamproject.entity.deserializer.RoomSerializer;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

public class EventDto implements Comparable<EventDto> {

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDateTime dateTime;

  private int duration;

  @JsonSerialize(using = RoomSerializer.class)
  private Room room;

  @JsonSerialize(using = EventTypeSerializer.class)
  private EventType eventType;

  public EventDto() {

  }

  public EventDto(Event event) {
    this.dateTime = event.getDateTime();
    this.duration = event.getDuration();
    this.room = event.getRoom();
    this.eventType = event.getEventType();
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
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
    return Objects.equals(dateTime, other.dateTime) && Objects.equals(eventType, other.eventType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dateTime, eventType);
  }

  @Override
  public int compareTo(EventDto event) {
    if (this.dateTime.isEqual(event.getDateTime())) {
      return 0;
    } else {
      if (this.dateTime.isAfter(event.getDateTime())) {
        return 1;
      } else {
        return -1;
      }
    }

  }

  @Override
  public String toString() {
    return "Event{"
        + ", dateTime='" + dateTime + '\''
        + ", duration='" + duration + '\''
        + ", room='" + room.getNumber() + '\''
        + ", eventType='" + eventType.getName() + '\''
        + '}';
  }
}
