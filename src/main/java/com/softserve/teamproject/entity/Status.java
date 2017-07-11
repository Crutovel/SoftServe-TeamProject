package com.softserve.teamproject.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "status")
public class Status {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
  private StatusCategory statusCategory;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
  private Set<Groups> groups;
  public Status() {
  }

  public Set<Groups> getGroups() {
    return groups;
  }

  public void setGroups(Set<Groups> groups) {
    this.groups = groups;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public StatusCategory getStatusCategory() {
    return statusCategory;
  }

  public void setStatusCategory(StatusCategory statusCategory) {
    this.statusCategory = statusCategory;
  }

  @Override
  public String toString() {
    return "Status{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", statusCategory=" + statusCategory
        + '}';
  }
}
