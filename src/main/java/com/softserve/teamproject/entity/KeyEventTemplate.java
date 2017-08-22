package com.softserve.teamproject.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "key_event_template")
public class KeyEventTemplate extends Template {

  @ManyToOne
  private EventType eventType;

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }
}
