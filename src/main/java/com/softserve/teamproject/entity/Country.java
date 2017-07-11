package com.softserve.teamproject.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
  private Set<Location> location;

  public Country() {
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

  public Set<Location> getLocations() {
    return location;
  }

  public void setLocations(Set<Location> location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "Country{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", locations=" + location
        + '}';
  }
}
