package com.softserve.teamproject.entity;

import com.softserve.teamproject.service.MessageByLocaleService;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;

@MappedSuperclass
public class Template implements Comparable<Template> {

  @Id
  @GeneratedValue
  private int id;

  @Column(name = "duration")
  private int duration;

  @ManyToOne
  private Strategy strategy;

  @Column(name = "template_order")
  private int order;

  @Column(name = "rel")
  private int rel;

  @Transient
  private MessageByLocaleService messageByLocaleService;

  @Autowired
  public void setMessageByLocaleService(MessageByLocaleService messageByLocaleService) {
    this.messageByLocaleService = messageByLocaleService;
  }

  public int getRel() {
    return rel;
  }

  public void setRel(int rel) {
    this.rel = rel;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Strategy getStrategy() {
    return strategy;
  }

  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Override
  public int compareTo(Template template) {
    if (getOrder() > template.getOrder()) {
      return 1;
    }
    if (getOrder() < template.getOrder()) {
      return -1;
    } else if (getOrder() == 0 && template.getOrder() == 0) {
      return 0;
    } else {
      throw new IllegalArgumentException(
          messageByLocaleService.getMessage("illegalArgs.template.invalid"));
    }
  }
}
