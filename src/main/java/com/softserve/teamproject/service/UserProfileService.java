package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.User;
import java.util.Set;

/**
 * Use for handle user profile information from/to database
 */
public interface UserProfileService {

  /**
   * Get info from database about user by his NickName
   * @param principalName user nick name
   * @return user from database by his nickname
   */
  User getUserProfile(String principalName);

  /**
   * Retrieve from database all groups in current user location
   * @param principalName user nick name
   * @return list of groups in current user location
   */
  Set<Group> getGroupsFromUserLocation(String principalName);
}
