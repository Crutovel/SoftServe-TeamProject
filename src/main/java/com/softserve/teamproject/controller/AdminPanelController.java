package com.softserve.teamproject.controller;

import com.softserve.teamproject.dto.UserDto;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Use for interact with user through browser
 */
@Controller
public class AdminPanelController {

  private UserService userService;

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/signin")
  public String signinPage(Model model) {
    return "signin";
  }

  @RequestMapping("/signin-error.html")
  public String signinError(Model model) {
    model.addAttribute("loginError", true);
    return "signin";
  }

  @RequestMapping("/admin")
  public String adminPage(Model model) {
    model.addAttribute("users",userService.getAllUserDto());
    return "admin";
  }

  @PostMapping(value = "/admin/user/delete", produces = "application/json")
  @ResponseBody
  public List<UserDto> removeUser(@RequestBody Integer userId){
    userService.deleteUser(userId);
    return userService.getAllUserDto();
  }
}
