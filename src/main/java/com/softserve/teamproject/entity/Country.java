package com.softserve.teamproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
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

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "country", cascade = CascadeType.ALL)
  private Set<Location> locations;

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

  @JsonIgnore
  public Set<Location> getLocations() {
    return locations;
  }

  public void setLocations(Set<Location> locations) {
    this.locations = locations;
  }

  @Override
  public String toString() {
    return "Country{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", locations=" + locations
        + '}';
  }
}
