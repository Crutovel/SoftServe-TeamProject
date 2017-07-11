package com.softserve.teamproject.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
  private Country country;

  @OneToOne
  @JoinColumn(name = "coordinator_id", referencedColumnName = "id", nullable = false)
  private User coordinator;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
  private Set<Group> groups;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
  private Set<User> users;

  public Location() {
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

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public User getCoordinator() {
    return coordinator;
  }

  public void setCoordinator(User coordinator) {
    this.coordinator = coordinator;
  }

  public Set<Group> getGroups() {
    return groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return "Location{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", country=" + country
        + ", coordinator=" + coordinator
        + ", groups=" + groups
        + ", users=" + users
        + '}';
  }
}