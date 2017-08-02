package com.softserve.teamproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Template {

  @Id
  @GeneratedValue
  private int id;

  @ManyToOne
  @JoinColumn(name = "event_type_id", referencedColumnName = "id")
  private EventType eventType;

  @Column(name = "duration")
  private int duration;

  @ManyToOne
  private Strategy strategy;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Strategy getStrategy() {
    return strategy;
  }

  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }
}
