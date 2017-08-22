package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.dto.EditUserDto;
import com.softserve.teamproject.dto.UserDto;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Role;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.LocationRepository;
import com.softserve.teamproject.repository.RoleRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private LocationRepository locationRepository;

  @Autowired
  public void setRoleRepository(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Autowired
  public void setLocationRepository(
      LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<UserDto> getAllUserDto() {
    List<User> users = userRepository.findAll();
    return users.stream().map(UserDto::new).collect(Collectors.toList());
  }

  @Override
  public void deleteUser(int userId) {
    userRepository.deleteEntity(userId);
  }

  @Override
  public EditUserDto findUser(int userId) {
    List<String> roles = roleRepository.findAll().stream().map(Role::getName)
        .collect(Collectors.toList());
    List<String> locations = locationRepository.findAll().stream().map(Location::getName).collect(
        Collectors.toList());
    EditUserDto editUserDto = null;
    try {
      if (userId == -1) {
        editUserDto = new EditUserDto();
      } else {
        User user = userRepository.findOne(userId);
        editUserDto = new EditUserDto(user);
      }
      return editUserDto;
    } finally {
      editUserDto.setLocations(locations);
      editUserDto.setRoles(roles);
    }
  }

  @Override
  public void saveUser(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    Location location = locationRepository.findByName(userDto.getLocation());
    user.setLocation(location);
    user.setFirstName(userDto.getFirstName());
    //user.setImage();
    user.setLastName(userDto.getLastName());
    user.setNickName(userDto.getLogin());
    user.setPassword(userDto.getPassword());
    Role role = roleRepository.findByName(userDto.getRole());
    user.setRole(role);
    userRepository.save(user);
  }
}
