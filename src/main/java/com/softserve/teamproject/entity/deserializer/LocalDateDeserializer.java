package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Class provides the implementation of the deserialization method to describe how extactly the json
 * date is going to be deserialized and parsed.
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

  @Override
  public LocalDate deserialize(JsonParser jp, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {

    ObjectCodec oc = jp.getCodec();
    TextNode node = (TextNode) oc.readTree(jp);
    String dateString = node.textValue();
    LocalDate date = LocalDate.parse(dateString);
    return date;
  }
}