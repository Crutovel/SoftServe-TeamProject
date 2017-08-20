package com.softserve.teamproject.entity.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.teamproject.entity.serializer.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import org.springframework.hateoas.ResourceSupport;

/**
 * Use for represent Event object in http response in HATEOAS style.
 */
public class EventResource extends ResourceSupport {

  private int eventId;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime start;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime end;

  public int getEventId() {
    return eventId;
  }

  public void setEventId(int eventId) {
    this.eventId = eventId;
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
}
