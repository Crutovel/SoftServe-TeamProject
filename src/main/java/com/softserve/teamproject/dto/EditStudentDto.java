package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.Student;
import java.util.List;

public class EditStudentDto extends StudentDto {
  private List<String> groups;
  private List<String> experts;
  private List<String> englishLevels;

  public EditStudentDto(Student student) {
    super(student);
  }

  public EditStudentDto(){}

  public List<String> getGroups() {
    return groups;
  }

  public void setGroups(List<String> groups) {
    this.groups = groups;
  }

  public List<String> getExperts() {
    return experts;
  }

  public void setExperts(List<String> experts) {
    this.experts = experts;
  }

  public List<String> getEnglishLevels() {
    return englishLevels;
  }

  public void setEnglishLevels(List<String> englishLevels) {
    this.englishLevels = englishLevels;
  }
}
