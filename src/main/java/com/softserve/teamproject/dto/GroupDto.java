package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.BudgetOwner;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.User;
import java.time.LocalDate;
import java.util.Set;

public class GroupDto {

  private int id;
  private String name;
  private Set<User> teachers;
  private Location location;
  private LocalDate startDate;
  private LocalDate finishDate;
  private Status status;
  private Specialization specialization;
  private Set<String> experts;
  private BudgetOwner budgetOwner;
  private boolean deleted;

  public GroupDto(Group group) {
    this.id = group.getId();
    this.name = group.getName();
    this.teachers = group.getTeachers();
    this.location = group.getLocation();
    this.startDate = group.getStartDate();
    this.finishDate = group.getFinishDate();
    this.status = group.getStatus();
    this.specialization = group.getSpecialization();
    this.experts = group.getExperts();
    this.budgetOwner = group.getBudgetOwner();
    this.deleted = group.isDeleted();
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

  public Set<User> getTeachers() {
    return teachers;
  }

  public void setTeachers(Set<User> teachers) {
    this.teachers = teachers;
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

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    deleted = deleted;
  }

  @Override
  public String toString() {
    return "GroupDto{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", teachers=" + teachers +
        ", location=" + location +
        ", startDate=" + startDate +
        ", finishDate=" + finishDate +
        ", status=" + status +
        ", specialization=" + specialization +
        ", experts=" + experts +
        ", budgetOwner=" + budgetOwner +
        ", isDeleted=" + deleted +
        '}';
  }
}
