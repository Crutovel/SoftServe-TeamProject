package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.softserve.teamproject.entity.Expert;
import com.softserve.teamproject.repository.ExpertRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

public class ExpertDeserializer extends JsonDeserializer<Expert> {

  private ExpertRepository expertRepository;

  @Autowired
  public void setExpertRepository(ExpertRepository expertRepository) {
    this.expertRepository = expertRepository;
  }

  @Override
  public Expert deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    return expertRepository.findOne(node.get("id").asInt());
  }

}