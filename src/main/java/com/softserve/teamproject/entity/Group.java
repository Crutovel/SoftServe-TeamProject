package com.softserve.teamproject.entity;

import com.softserve.teamproject.entity.enums.BudgetOwner;
import java.util.Set;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
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
@Table(name = "educational_group")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "teacher_id", referencedColumnName = "id", nullable = false)
  private User teacher;

  @ManyToOne
  @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
  private Location location;

  @Column(name = "start_date", columnDefinition = "DATE")
  private LocalDate startDate;

  @Column(name = "finish_date", columnDefinition = "DATE")
  private LocalDate finishDate;

  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "specialization_id", referencedColumnName = "id", nullable = false)
  private Specialization specialization;

  @ElementCollection
  @CollectionTable(name = "expert",
          joinColumns = @JoinColumn(name = "edu_group_id")
  )
  @Column(name = "expert_name")
  private Set<String> experts;

  @Enumerated(EnumType.STRING)
  private BudgetOwner budgetOwner;

  public Group() {
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
    return teacher;
  }

  public void setTeacher(User teacher) {
    this.teacher = teacher;
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

//  public void setSpecialization(Specialization specialization) {
//    this.specialization = specialization;
//  }
//
  public Set<String> getExperts() {
    return experts;
  }

  public void setExperts(Set<String> experts) {
    this.experts = experts;
  }

  public BudgetOwner getBudgetOwner() {
    return budgetOwner;
  }

  public void setBudgetOwner(BudgetOwner budgetOwner) {
    this.budgetOwner = budgetOwner;
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
    Group other = (Group) otherObject;
    return Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Group{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", teacher=" + teacher
        + ", location=" + location
        + ", startDate=" + startDate
        + ", finishDate=" + finishDate
        + ", status=" + status
        + ", specialization=" + specialization
        + '}';
  }
}
