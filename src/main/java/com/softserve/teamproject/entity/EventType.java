package com.softserve.teamproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
    import java.util.List;
    import java.util.Objects;
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    import javax.persistence.OneToMany;
    import javax.persistence.Table;

@Entity
@Table(name = "event_type")
public class EventType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "is_key_date")
  private boolean isKeyDate;

  @OneToMany(mappedBy = "eventType")
  @JsonIgnore
  private List<Template> templates;

  public List<Template> getTemplates() {
    return templates;
  }

  public void setTemplates(List<Template> templates) {
    this.templates = templates;
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

  public boolean isKeyDate() {
    return isKeyDate;
  }

  public void setKeyDate(boolean keyDate) {
    isKeyDate = keyDate;
  }

  public void setName(String name) {
    this.name = name;
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
    EventType other = (EventType) otherObject;
    return Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "EventType{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", isKeyDate='" + isKeyDate + '\''
        + '}';
  }
}
