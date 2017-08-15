package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.softserve.teamproject.entity.EventType;
import java.io.IOException;

public class EventTypeSerializer extends JsonSerializer<EventType> {

  @Override
  public void serialize(EventType eventType, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    generator.writeString(eventType.getName());
  }
}