package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUserProfile(String principalName) {
    User user = userRepository.getUserByNickName(principalName);
    return user;
  }
}