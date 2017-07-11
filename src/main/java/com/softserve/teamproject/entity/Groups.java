package com.softserve.teamproject.entity;

import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Groups {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "teacher_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "location_id", nullable = false)
  private Location location;

  @ManyToOne
  @JoinColumn(name = "status_id", nullable = false)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "specialization_id", nullable = false)
  private Specialization specialization;

  @Column(name = "start_date")
  private LocalDate startDate;

  @Column(name = "finish_date")
  private LocalDate finishDate;

  public Groups() {
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

  public User getTeacher() {
    return user;
  }

  public void setTeacher(User user) {
    this.user = user;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(LocalDate finishDate) {
    this.finishDate = finishDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Specialization getSpecialization() {
    return specialization;
  }

  public void setSpecialization(Specialization specialization) {
    this.specialization = specialization;
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
    Groups other = (Groups) otherObject;
    return Objects.equals(id, other.id) && Objects.equals(name, other.name)
        && Objects.equals(user, other.user) && Objects.equals(location, other.location)
        && Objects.equals(startDate, other.startDate) && Objects.equals(finishDate, other.finishDate)
        && Objects.equals(status, other.status) && Objects.equals(specialization, other.specialization);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, user, location, startDate, finishDate, status, specialization);
  }

  @Override
  public String toString() {
    return "Group{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", teacher=" + user
        + ", location=" + location
        + ", startDate=" + startDate
        + ", finishDate=" + finishDate
        + ", status=" + status
        + ", specialization=" + specialization
        + '}';
  }
}
