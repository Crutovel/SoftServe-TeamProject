package com.softserve.teamproject.generator;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Template;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateGenerator {

  private Map<Template, LocalDate> validationTemplate;
  private List<Template> templates;

  protected Map<Template, LocalDate> generateTemplates(List<Template> templates, Group group) {
    this.templates = templates;
    this.templates.sort(Comparator.naturalOrder());
    Template template = getFirst();
    validationTemplate = new HashMap<>();
    validationTemplate.put(template, group.getStartDate().plusDays(template.getDuration()));
    validationTemplate.put(getLast(), group.getFinishDate());
    while (this.templates.size() > 0) {
      fillOtherTemplates();
    }
    return validationTemplate;
  }

  private Template getFirst() {
    Template template = templates.get(0);
    templates.remove(template);
    return template;
  }

  private Template getLast() {
    Template template = templates.get(templates.size() - 1);
    templates.remove(template);
    return template;
  }

  private void fillOtherTemplates() {
    Template template = getFirst();
    LocalDate from = getDateByLink(template.getRel());
    validationTemplate.put(template,from.plusDays(template.getDuration()));
  }

  private LocalDate getDateByLink(Integer rel) {
    for (Map.Entry<Template, LocalDate>  entry : validationTemplate.entrySet()) {
      if (entry.getKey().getRel() == rel) {
        return entry.getValue();
      }
    }
    throw new IllegalArgumentException();
  }
}
