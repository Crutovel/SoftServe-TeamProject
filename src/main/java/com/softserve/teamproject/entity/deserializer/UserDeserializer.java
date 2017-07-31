package com.softserve.teamproject.entity.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDeserializer extends JsonDeserializer<Set<User>> {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Set<User> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    Set<User> users = new HashSet<>();
    ObjectCodec oc = jsonParser.getCodec();
    JsonNode node = oc.readTree(jsonParser);
    if (node.isArray()) {
      for (final JsonNode objNode : node) {
        User user = userRepository.findOne(objNode.get("id").asInt());
        users.add(user);
      }
    }
    return users;
  }
}