package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.dto.UserDto;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<UserDto> getAllUserDto() {
    List<User> users=userRepository.findAll();
    return users.stream().map(UserDto::new).collect(Collectors.toList());
  }

  @Override
  public void deleteUser(int userId) {
    userRepository.deleteEntity(userId);
//    userRepository.delete(userId);
  }

  @Override
  public UserDto findUser(int userId) {
    User user=userRepository.findOne(userId);
    UserDto userDto=new UserDto(user);
    return userDto;
  }
}
