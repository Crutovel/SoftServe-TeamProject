package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  @Override
  public void serialize(LocalDateTime date, JsonGenerator generator,
      SerializerProvider provider) throws IOException,
      JsonProcessingException {

    String dateString = date.format(formatter);
    generator.writeString(dateString);
  }
}