package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.repository.RoomRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomDeserializer extends JsonDeserializer<Room> {

  private RoomRepository roomRepository;

  @Autowired
  public void setRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @Override
  public Room deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    Room room = roomRepository.findOne(node.get("id").asInt());
    return room;
  }
}
