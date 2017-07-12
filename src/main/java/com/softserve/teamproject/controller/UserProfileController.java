package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.service.UserProfileService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle user profile.
 * User photo is converted into JSON format as a Base64 string
 */
@RestController
public class UserProfileController {

  private UserProfileService userProfileService;

  @Autowired
  public void setUserProfileService(UserProfileService userProfileService) {
    this.userProfileService = userProfileService;
  }

  /**
   * Get user info of current user
   *
   * @param principal authorized user
   * @return user info of authorized user
   */
  @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
  public
  @ResponseBody
  User getUserProfile(Principal principal) {
    User user = userProfileService.getUserProfile(principal.getName());
    return user;
  }
}
