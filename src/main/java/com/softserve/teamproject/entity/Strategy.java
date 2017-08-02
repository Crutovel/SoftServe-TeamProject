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
  private List<Template> templates;
  @OneToMany(mappedBy = "strategy")
  private List<Specialization> specializations;

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

  public List<Template> getTemplates() {
    return templates;
  }

  public void setTemplates(List<Template> templates) {
    this.templates = templates;
  }

}
