package com.softserve.teamproject.generator;

import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.KeyEventTemplate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class KeyEventGenerator extends TemplateGenerator {


  public Map<EventType, LocalDate> generateKeyEventTemplates(List<KeyEventTemplate> templates,
      Group group) {
    return generateTemplates(new ArrayList<>(templates), group).entrySet().stream().collect(
        Collectors.toMap(
            (entry -> ((KeyEventTemplate) entry.getKey()).getEventType()), Map.Entry::getValue));
  }
}
