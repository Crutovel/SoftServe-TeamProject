package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.repository.EventTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class EventTypeDeserializer extends JsonDeserializer<EventType> {

    private EventTypeRepository eventTypeRepository;

    @Autowired
    public void setEventTypeRepository(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public EventType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return eventTypeRepository.findOne(node.get("id").asInt());
    }

}
