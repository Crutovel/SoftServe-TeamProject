package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.repository.SpecializationRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpecializationDeserializer extends JsonDeserializer<Specialization> {

  private SpecializationRepository specializationRepository;

  @Autowired
  public void setSpecializationRepository(SpecializationRepository specializationRepository) {
    this.specializationRepository = specializationRepository;
  }

  @Override
  public Specialization deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    return specializationRepository.findOne(node.get("id").asInt());
  }
}
