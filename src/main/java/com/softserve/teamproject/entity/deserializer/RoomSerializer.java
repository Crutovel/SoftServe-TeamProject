package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.softserve.teamproject.entity.Room;
import java.io.IOException;

public class RoomSerializer extends JsonSerializer<Room> {

  @Override
  public void serialize(Room room, JsonGenerator generator,
      SerializerProvider provider) throws IOException {
    generator.writeString(room.getNumber());
  }
}
