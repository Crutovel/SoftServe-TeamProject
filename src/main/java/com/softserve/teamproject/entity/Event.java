package com.softserve.teamproject.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.teamproject.entity.deserializer.EventTypeDeserializer;
import com.softserve.teamproject.entity.deserializer.GroupDeserializer;
import com.softserve.teamproject.entity.deserializer.LocalDateTimeDeserializer;
import com.softserve.teamproject.entity.deserializer.RoomDeserializer;

import com.softserve.teamproject.entity.serializer.LocalDateTimeSerializer;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "event")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "start")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime start;

  @Column(name = "end")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime end;

  @ManyToOne
  @JoinColumn(name = "room_id", referencedColumnName = "id")
  @JsonDeserialize(using = RoomDeserializer.class)
  private Room room;

  @ManyToOne
  @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
  @JsonDeserialize(using = GroupDeserializer.class)
  private Group group;

  @ManyToOne
  @JoinColumn(name = "event_type_id", referencedColumnName = "id", nullable = false)
  @JsonDeserialize(using = EventTypeDeserializer.class)
  private EventType eventType;

  public Event(Integer id, LocalDateTime start, LocalDateTime end, Room room, Group group,
      EventType eventType) {
    this.id = id;
    this.start = start;
    this.end = end;
    this.room = room;
    this.group = group;
    this.eventType = eventType;
  }

  public Event(Event event) {
    this.start = event.getStart();
    this.end = event.getEnd();
    this.room = event.getRoom();
    this.group = event.getGroup();
    this.eventType = event.getEventType();
  }

  public Event() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
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
    Event other = (Event) otherObject;
    return Objects.equals(start, other.start) && Objects.equals(end, other.end)
        && Objects.equals(group, other.group)
        && Objects.equals(eventType, other.eventType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end, group, eventType);
  }

  @Override
  public String toString() {
    return "Event{"
        + "id=" + id
        + ", start='" + start + '\''
        + ", end='" + end + '\''
        + ", group='" + group.getName() + '\''
        + ", eventType='" + eventType.getName() + '\''
        + '}';
  }
}
