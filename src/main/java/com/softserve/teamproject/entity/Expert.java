package com.softserve.teamproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "expert")
public class Expert {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "expert_name")
  private String expertName;

  public Expert() {
  }

  public Expert(String name) {
    this.expertName = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getExpertName() {
    return expertName;
  }

  public void setExpertName(String expertName) {
    this.expertName = expertName;
  }
}
