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
@Table(name = "contact_link")
public class ContactLink {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "value")
  private String value;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  public ContactLink() {
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
    ContactLink other = (ContactLink) otherObject;
    return Objects.equals(id, other.id) && Objects.equals(value, other.value)
        && Objects.equals(user, other.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value, user);
  }

  @Override
  public String toString() {
    return "ContactLink{"
        + "id=" + id
        + ", value='" + value + '\''
        + ", user=" + user
        + '}';
  }
}
