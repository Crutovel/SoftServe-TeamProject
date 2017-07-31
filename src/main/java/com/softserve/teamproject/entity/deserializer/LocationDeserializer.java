package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.repository.LocationRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationDeserializer extends JsonDeserializer<Location> {

  private LocationRepository locationRepository;

  @Autowired
  public void setLocationRepository(
      LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  @Override
  public Location deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    return locationRepository.findOne(node.get("id").asInt());
  }
}
