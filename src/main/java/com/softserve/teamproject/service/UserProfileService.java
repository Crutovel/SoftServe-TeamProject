package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.User;

/**
 * use for handle user profile information from/to database
 */
public interface UserProfileService {

  /**
   * get info from database about user by his NickName
   * @param principalName user nick name
   * @return user from database by his nickname
   */
  User getUserProfile(String principalName);
}
