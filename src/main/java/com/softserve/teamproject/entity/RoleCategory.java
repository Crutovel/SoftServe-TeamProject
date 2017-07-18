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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "role_category")
public class RoleCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @LazyCollection(LazyCollectionOption.FALSE)
  @OneToMany(mappedBy = "roleCategory", cascade = CascadeType.ALL)
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
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null) {
      return false;
    }
    if (getClass() != otherObject.getClass()) {
      return false;
    }
    RoleCategory other = (RoleCategory) otherObject;
    return Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
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
