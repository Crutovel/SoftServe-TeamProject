package com.softserve.teamproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

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

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @NaturalId
  @Column(name = "nick_name")
  private String nickName;

  @Column(name = "password_hash_code")
  private String password;

  @Column(name = "self_info")
  private String selfInfo;

  @Lob
  @Column(name = "image")
  private byte[] image;

  @ManyToOne
  @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
  private Location location;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Set<Email> emails;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Set<Phone> phones;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Set<ContactLink> contactLinks;

  public User() {
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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

  public String getSelfInfo() {
    return selfInfo;
  }

  public void setSelfInfo(String selfInfo) {
    this.selfInfo = selfInfo;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Set<Email> getEmails() {
    return emails;
  }

  public void setEmails(Set<Email> emails) {
    this.emails = emails;
  }

  public Set<Phone> getPhones() {
    return phones;
  }

  public void setPhones(Set<Phone> phones) {
    this.phones = phones;
  }

  public Set<ContactLink> getContactLinks() {
    return contactLinks;
  }

  public void setContactLinks(Set<ContactLink> contactLinks) {
    this.contactLinks = contactLinks;
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
//    return Objects.equals(id, other.id) && Objects.equals(firstName, other.firstName)
//        && Objects.equals(lastName, other.lastName) && Objects.equals(role, other.role)
//        && Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(nickName, other.nickName)
//        && Objects.equals(password, other.password) && Objects.equals(selfInfo, other.selfInfo)
//        && Arrays.equals(image, other.image) && Objects.equals(location, other.location)
//        && Objects.equals(emails, other.emails) && Objects.equals(phones, other.phones)
//        && Objects.equals(contactLinks, other.contactLinks);
    return Objects.equals(id, other.id) && Objects.equals(nickName, other.nickName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nickName);
  }

  @Override
  public String toString() {
    return "User{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", role=" + role
        + ", dateOfBirth=" + dateOfBirth
        + ", nickName='" + nickName + '\''
        + ", password='" + password + '\''
        + ", selfInfo='" + selfInfo + '\''
        + ", image=" + Arrays.toString(image)
//        + ", location=" + location
        + ", emails=" + emails
        + ", phones=" + phones
        + ", contactLinks=" + contactLinks
        + '}';
  }
}
