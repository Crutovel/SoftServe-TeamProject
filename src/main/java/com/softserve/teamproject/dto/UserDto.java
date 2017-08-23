package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Role;
import com.softserve.teamproject.entity.User;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Used in admin.html
 */
public class UserDto {

  public static final String PATH = System.getProperty("user.dir")
           + "/src/main/resources/images/";
  private int id;
  private String firstName;
  private String lastName;
  private String role;
  private String location;
  private String photo;
  private String login;
  private String password;

  public UserDto(User user){
    id=user.getId();
    firstName=user.getFirstName();
    lastName=user.getLastName();
    role=user.getRole().getName();
    location=user.getLocation().getName();
    photo="";
    login=user.getNickName();
    password=user.getPassword();
  }

  public  UserDto(){}

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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  
   public static User setImageAsBase64(User user) {
        ImageMaster master = new ImageMaster();
        Path path = Paths.get(PATH + user.getImage());
        String imageBase64 = master.encodeImage(path);
        User retUser = cloneUser(user);
        retUser.setImage(imageBase64);
        return retUser;
    }

    private static User cloneUser(User user) {
        User retUser = new User();
        retUser.setId(user.getId());
        retUser.setFirstName(user.getFirstName());
        retUser.setLastName(user.getLastName());
        retUser.setLocation(user.getLocation());
        retUser.setRole(user.getRole());
        retUser.setNickName(user.getNickName());
        retUser.setPassword(user.getPassword());
        return retUser;
    }
}
