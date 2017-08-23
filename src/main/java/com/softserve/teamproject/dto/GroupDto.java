package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.BudgetOwner;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupDto {

  private int id;
  private String name;
  private List<String> teachers;
  private String location;
  private LocalDate startDate;
  private LocalDate finishDate;
  private String status;
  private String specialization;
  private Set<String> experts;
  private String budgetOwner;
  private boolean deleted;

  public GroupDto() {
  }

  public GroupDto(Group group) {
    this.id = group.getId();
    this.name = group.getName();
    this.teachers = group.getTeachers().stream()
        .map(u -> u.getFirstName() + " " + u.getLastName() + " (" + u.getNickName() + ")")
        .collect(Collectors.toList());
    this.location = group.getLocation().getName();
    this.startDate = group.getStartDate();
    this.finishDate = group.getFinishDate();
    this.status = group.getStatus().getName();
    this.specialization = group.getSpecialization().getName();
    this.experts = group.getExperts();
    this.budgetOwner = group.getBudgetOwner().getName();
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

  public List<String> getTeachers() {
    return teachers;
  }

  public void setTeachers(List<String> teachers) {
    this.teachers = teachers;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSpecialization() {
    return specialization;
  }

  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }

  public Set<String> getExperts() {
    return experts;
  }

  public void setExperts(Set<String> experts) {
    this.experts = experts;
  }

  public String getBudgetOwner() {
    return budgetOwner;
  }

  public void setBudgetOwner(String budgetOwner) {
    this.budgetOwner = budgetOwner;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  @Override
  public String toString() {
    return "GroupDto{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", teachers=" + teachers +
        ", location='" + location + '\'' +
        ", startDate=" + startDate +
        ", finishDate=" + finishDate +
        ", status='" + status + '\'' +
        ", specialization='" + specialization + '\'' +
        ", experts=" + experts +
        ", budgetOwner='" + budgetOwner + '\'' +
        ", deleted=" + deleted +
        '}';
  }
}
