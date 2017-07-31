package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.repository.StatusRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusDeserializer extends JsonDeserializer<Status> {

  private StatusRepository statusRepository;

  @Autowired
  public void setStatusRepository(StatusRepository statusRepository) {
    this.statusRepository = statusRepository;
  }

  @Override
  public Status deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    return statusRepository.findOne(node.get("id").asInt());
  }
}