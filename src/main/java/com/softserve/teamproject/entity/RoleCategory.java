package com.softserve.teamproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "role_category")
public class RoleCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "roleCategory", cascade = CascadeType.ALL)
  private Set<Role> roles;

  public RoleCategory() {
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
  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "RoleCategory{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", roles=" + roles
        + '}';
  }
}
