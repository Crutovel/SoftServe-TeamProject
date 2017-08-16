package com.softserve.teamproject.entity.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

/**
 * Use for represent Student object in http response in HATEOAS style.
 */
@Component
public class StudentResource extends ResourceSupport {

  private int studentId;

  private String firstName;

  private String lastName;

  private byte[] image;

  private byte[] cv;

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
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

  public byte[] getCv() {
    return cv;
  }

  public void setCv(byte[] cv) {
    this.cv = cv;
  }
}
