package com.softserve.teamproject.dto;

import com.softserve.teamproject.entity.User;
import java.util.List;

/**
 * Use for create/edit user info at admin.html
 */
public class EditUserDto extends UserDto {

  private List<String> roles;
  private List<String> locations;

  public EditUserDto(User user) {
    super(user);
  }

  public EditUserDto() {}

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public List<String> getLocations() {
    return locations;
  }

  public void setLocations(List<String> locations) {
    this.locations = locations;
  }
}
