package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
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
      throws IOException {

    ObjectCodec oc = jp.getCodec();
    TextNode node = oc.readTree(jp);
    String dateString = node.textValue();
    return LocalDate.parse(dateString);
  }
}