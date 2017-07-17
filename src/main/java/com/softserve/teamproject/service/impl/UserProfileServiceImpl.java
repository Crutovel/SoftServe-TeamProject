package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.LocationRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.UserProfileService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  private UserRepository userRepository;
  private LocationRepository locationRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setLocationRepository(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  @Override
  public User getUserProfile(String principalName) {
    return userRepository.getUserByNickName(principalName);
  }

  @Override
  public Set<Group> getGroupsFromUserLocation(String principalName) {
    User currentUser=userRepository.getUserByNickName(principalName);
    Location userLocation=locationRepository.findOne(currentUser.getLocation().getId());
    return userLocation.getGroups();
  }
}