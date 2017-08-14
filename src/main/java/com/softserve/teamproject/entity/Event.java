package com.softserve.teamproject.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softserve.teamproject.entity.deserializer.EventTypeDeserializer;
import com.softserve.teamproject.entity.deserializer.GroupDeserializer;
import com.softserve.teamproject.entity.deserializer.LocalDateTimeDeserializer;
import com.softserve.teamproject.entity.deserializer.RoomDeserializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "event")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "datetime")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime dateTime;

  @Column(name = "duration")
  private int duration;

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

  public Event(Integer id, LocalDateTime dateTime, int duration, Room room, Group group,
      EventType eventType) {
    this.id = id;
    this.dateTime = dateTime;
    this.duration = duration;
    this.room = room;
    this.group = group;
    this.eventType = eventType;
  }

  public Event(Event event) {
    this.dateTime = event.getDateTime();
    this.duration = event.getDuration();
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

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
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

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
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
    return Objects.equals(dateTime, other.dateTime) && Objects.equals(group, other.group)
        && Objects.equals(eventType, other.eventType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dateTime, group, eventType);
  }

  @Override
  public String toString() {
    return "Event{"
        + "id=" + id
        + ", dateTime='" + dateTime + '\''
        + ", duration='" + duration + '\''
        + ", group='" + group.getName() + '\''
        + ", eventType='" + eventType.getName() + '\''
        + '}';
  }
}
