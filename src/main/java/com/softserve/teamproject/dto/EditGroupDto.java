package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.Group;
import java.util.List;

public class EditGroupDto extends GroupDto {

  private List<String> listTeachers;
  private List<String> locations;
  private List<String> statuses;
  private List<String> specializations;
  private List<String> budgetOwners;

  public EditGroupDto(Group group) {
    super(group);
  }

  public EditGroupDto() {

  }

  public List<String> getListTeachers() {
    return listTeachers;
  }

  public void setListTeachers(List<String> listTeachers) {
    this.listTeachers = listTeachers;
  }

  public List<String> getLocations() {
    return locations;
  }

  public void setLocations(List<String> locations) {
    this.locations = locations;
  }

  public List<String> getStatuses() {
    return statuses;
  }

  public void setStatuses(List<String> statuses) {
    this.statuses = statuses;
  }

  public List<String> getSpecializations() {
    return specializations;
  }

  public void setSpecializations(List<String> specializations) {
    this.specializations = specializations;
  }

  public List<String> getBudgetOwners() {
    return budgetOwners;
  }

  public void setBudgetOwners(List<String> budgetOwners) {
    this.budgetOwners = budgetOwners;
  }
}
