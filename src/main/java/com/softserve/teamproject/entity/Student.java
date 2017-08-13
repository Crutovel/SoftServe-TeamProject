package com.softserve.teamproject.entity;

import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Lob
  @Column(name = "image")
  private byte[] image;

  @ManyToOne
  @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
  private Group group;

  @ManyToOne
  @JoinColumn(name = "english_level_id", referencedColumnName = "id", nullable = false)
  private EnglishLevel englishLevel;

  @Column(name="incoming_test")
  private int incomingTest;

  @Column(name="entry_score")
  private double entryScore;

  @ManyToOne
  @JoinColumn(name = "approved_by_expert_id", referencedColumnName = "id", nullable = false)
  private Expert testApprovedByExpert;

  public int getIncomingTest() {
    return incomingTest;
  }

  public void setIncomingTest(int incomingTest) {
    this.incomingTest = incomingTest;
  }

  public double getEntryScore() {
    return entryScore;
  }

  public void setEntryScore(double entryScore) {
    this.entryScore = entryScore;
  }

  public Expert getTestApprovedByExpert() {
    return testApprovedByExpert;
  }

  public void setTestApprovedByExpert(Expert testApprovedByExpert) {
    this.testApprovedByExpert = testApprovedByExpert;
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

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public EnglishLevel getEnglishLevel() {
    return englishLevel;
  }

  public void setEnglishLevel(EnglishLevel englishLevel) {
    this.englishLevel = englishLevel;
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
    Student other = (Student) otherObject;
    return Objects.equals(firstName, other.getFirstName()) && Objects
        .equals(lastName, other.getLastName()) && Objects
        .equals(group, other.group);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, group.getId());
  }

  @Override
  public String toString() {
    return "User{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", image=" + Arrays.toString(image)
        + ", group='" + group.getName() + '\''
        + ", englishLevel='" + englishLevel.getName() + '\''
        + '}';
  }
}
