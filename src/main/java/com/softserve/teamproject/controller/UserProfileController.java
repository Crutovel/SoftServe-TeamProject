package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.service.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle user profile and related to user information.
 * User photo is converted into JSON format as a Base64 string.
 */
@RestController
@Api(value = "studentController", description = "Operations with students")
public class UserProfileController {

  private UserProfileService userProfileService;

  @Autowired
  public void setUserProfileService(UserProfileService userProfileService) {
    this.userProfileService = userProfileService;
  }

  /**
   * Get user info of current user.
   *
   * @param principal authorized user
   * @return user info of authorized user
   */
  @GetMapping(value = "/user/profile", produces = "application/json")
  @ApiOperation(value = "Get user info of current user")
  public User getUserProfile(Principal principal) {
    return userProfileService.getUserProfile(principal.getName());
  }
}
