package com.softserve.teamproject.generator;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.StatusTemplate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class StatusGenerator extends TemplateGenerator{

  public Map<Status, LocalDate> generateKeyEventTemplates(List<StatusTemplate> templates,
      Group group) {
    return generateTemplates(new ArrayList<>(templates), group).entrySet().stream().collect(
        Collectors.toMap(
            (entry -> ((StatusTemplate) entry.getKey()).getStatus()), Map.Entry::getValue));
  }
}
