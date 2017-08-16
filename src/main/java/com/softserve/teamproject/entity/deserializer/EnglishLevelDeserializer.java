package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.softserve.teamproject.entity.EnglishLevel;
import com.softserve.teamproject.repository.EnglishLevelRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

public class EnglishLevelDeserializer extends JsonDeserializer<EnglishLevel> {

  private EnglishLevelRepository englishLevelRepository;

  @Autowired
  public void setEnglishLevelRepository(EnglishLevelRepository englishLevelRepository) {
    this.englishLevelRepository = englishLevelRepository;
  }

  @Override
  public EnglishLevel deserialize(JsonParser jsonParser,
      DeserializationContext deserializationContext) throws IOException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    EnglishLevel englishLevel = englishLevelRepository.findOne(node.get("id").asInt());
    return englishLevel;
  }
}
