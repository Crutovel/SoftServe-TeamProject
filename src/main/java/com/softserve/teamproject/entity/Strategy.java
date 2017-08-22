package com.softserve.teamproject.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Strategy {

  @Id
  @GeneratedValue
  private int id;
  @OneToMany(mappedBy = "strategy")
  private List<KeyEventTemplate> keyEventTemplates;
  @OneToMany(mappedBy = "strategy")
  private List<StatusTemplate> statusTemplates;
  @OneToMany(mappedBy = "strategy")
  private List<Specialization> specializations;

  public List<StatusTemplate> getStatusTemplates() {
    return statusTemplates;
  }

  public void setStatusTemplates(
      List<StatusTemplate> statusTemplates) {
    this.statusTemplates = statusTemplates;
  }

  public List<Specialization> getSpecializations() {
    return specializations;
  }

  public void setSpecializations(
      List<Specialization> specializations) {
    this.specializations = specializations;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<KeyEventTemplate> getKeyEventTemplates() {
    return keyEventTemplates;
  }

  public void setKeyEventTemplates(List<KeyEventTemplate> keyEventTemplates) {
    this.keyEventTemplates = keyEventTemplates;
  }
}
