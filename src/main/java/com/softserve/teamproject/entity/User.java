package com.softserve.teamproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softserve.teamproject.utils.ImageMaster;
import java.nio.file.Path;
import java.nio.file.Paths;
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
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @ManyToOne
  @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
  private Role role;

  @Column(name = "nick_name")
  private String nickName;

  @Column(name = "password_hash_code")
  private String password;

  @Column(name = "image")
  private String image;

  @ManyToOne
  @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
  private Location location;

  private static final String PATH = System.getProperty("user.dir")
      + "/src/main/resources/images/";

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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setImageAsBase64() {
    ImageMaster master = new ImageMaster();
    Path path = Paths.get(PATH + getImage());
    String imageBase64 = master.encodeImage(path);
    setImage(imageBase64);
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
    User other = (User) otherObject;
    return Objects.equals(nickName, other.nickName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nickName);
  }

  @Override
  public String toString() {
    return "User{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", role=" + role
        + ", nickName='" + nickName + '\''
        + ", password='" + password + '\''
        + ", image=" + image
        + '}';
  }
}
