package com.softserve.teamproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "email")
public class Email {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "value")
  private String value;

  @Column(name = "is_it_primary")
  private boolean isPrimary;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  public Email() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isPrimary() {
    return isPrimary;
  }

  public void setPrimary(boolean primary) {
    isPrimary = primary;
  }

  @JsonIgnore
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Email{"
        + "id=" + id
        + ", value='" + value + '\''
        + ", isPrimary=" + isPrimary
        + ", user=" + user
        + '}';
  }
}
