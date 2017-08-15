package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.softserve.teamproject.entity.Group;
import java.io.IOException;

public class GroupSerializer extends JsonSerializer<Group> {

  @Override
  public void serialize(Group group, JsonGenerator generator,
      SerializerProvider provider) throws IOException {

    generator.writeString(group.getName());
  }
}
