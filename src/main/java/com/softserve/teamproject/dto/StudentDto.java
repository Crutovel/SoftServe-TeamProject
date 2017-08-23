package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.Student;

public class StudentDto {

  private int id;
  private String firstName;
  private String lastName;
  private byte[] image;
  private String imageName;
  private byte[] cv;
  private String cvName;
  private String group;
  private String englishLevel;
  private Integer incomingTest;
  private Double entryScore;
  private String expert;

  public StudentDto(Student student) {
    this.id = student.getId();
    this.firstName = student.getFirstName();
    this.lastName = student.getLastName();
    this.image = student.getImage();
    this.imageName = student.getImageName();
    this.cv = student.getCv();
    this.cvName = student.getCvName();
    this.group = student.getGroup().getName();
    this.englishLevel = student.getEnglishLevel().getName();
    this.incomingTest = student.getIncomingTest();
    this.entryScore = student.getEntryScore();
    this.expert = student.getTestApprovedByExpert().getExpertName();
  }


  public Integer getIncomingTest() {
    return incomingTest;
  }

  public void setIncomingTest(Integer incomingTest) {
    this.incomingTest = incomingTest;
  }

  public Double getEntryScore() {
    return entryScore;
  }

  public void setEntryScore(Double entryScore) {
    this.entryScore = entryScore;
  }

  public String getExpert() {
    return expert;
  }

  public void setTestApprovedByExpert(String expert) {
    this.expert = expert;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public byte[] getCv() {
    return cv;
  }

  public void setCv(byte[] cv) {
    this.cv = cv;
  }

  public String getCvName() {
    return cvName;
  }

  public void setCvName(String cvName) {
    this.cvName = cvName;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getEnglishLevel() {
    return englishLevel;
  }

  public void setEnglishLevel(String englishLevel) {
    this.englishLevel = englishLevel;
  }

  public StudentDto(){}
}