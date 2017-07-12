package com.softserve.teamproject.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
  private RoleCategory roleCategory;

  public Role() {
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

  public RoleCategory getRoleCategory() {
    return roleCategory;
  }

  public void setRoleCategory(RoleCategory roleCategory) {
    this.roleCategory = roleCategory;
  }

  @Override
  public String toString() {
    return "Role{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", roleCategory=" + roleCategory
        + '}';
  }
}
