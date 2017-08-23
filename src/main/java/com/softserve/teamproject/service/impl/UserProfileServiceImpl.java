package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.dto.UserDto;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUserProfile(String principalName) {
    return userRepository.getUserByNickName(principalName);
  }

  @Override
  public User getUserProfileWithImage(String principalName) {
    return userRepository.getUserByNickNameWithImage(principalName);
  }
}