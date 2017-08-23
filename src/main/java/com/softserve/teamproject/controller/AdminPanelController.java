package com.softserve.teamproject.controller;

import com.softserve.teamproject.dto.EditStudentDto;
import com.softserve.teamproject.dto.EditUserDto;
import com.softserve.teamproject.dto.StudentDto;
import com.softserve.teamproject.dto.UserDto;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.StudentService;
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
  private StudentService studentService;
  private GroupService groupService;

  @Autowired
  public void setStudentService(StudentService studentService){
    this.studentService=studentService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setGroupService(GroupService groupService) {
    this.groupService = groupService;
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
    model.addAttribute("users", userService.getAllUserDto());
    return "admin";
  }

  @PostMapping(value = "/admin/user/delete", produces = "application/json")
  @ResponseBody
  public List<UserDto> removeUser(@RequestBody Integer userId) {
    userService.deleteUser(userId);
    return userService.getAllUserDto();
  }


  @PostMapping(value = "/admin/user/data", produces = "application/json")
  @ResponseBody
  public EditUserDto getUserInfo(@RequestBody Integer userId) {
    EditUserDto editUserDto = userService.findUser(userId);
    return editUserDto;
  }

  /**
   * Controller for entry point that edit or add new use
   *
   * @param user new or edited user
   * @return updated list of users
   */
  @PostMapping(value = "/admin/user/save", produces = "application/json")
  @ResponseBody
  public List<UserDto> saveUserInfo(@RequestBody UserDto user) {
    userService.saveUser(user);
    return userService.getAllUserDto();
  }


  @RequestMapping("/allStudents")
  public String seeAllStudents(Model model){
    model.addAttribute("students", studentService.getAllStudentDto());
    return "allStudents";
  }

  @PostMapping(value = "/admin/student/data", produces = "application/json")
  @ResponseBody
  public EditStudentDto getStudentInfo(@RequestBody Integer studentId) {
    EditStudentDto editStudentDto = studentService.findStudentToEdit(studentId);
    return editStudentDto;
  }

  @PostMapping(value = "/admin/student/save", produces = "application/json")
  @ResponseBody
  public List<StudentDto> saveStudent(@RequestBody StudentDto student) {
    studentService.saveStudent(student);
    return studentService.getAllStudentDto();
  }

  @PostMapping(value = "/admin/student/delete", produces = "application/json")
  @ResponseBody
  public List<StudentDto> removeStudent(@RequestBody Integer studentId) {
    studentService.deleteStudent(studentId);
    return studentService.getAllStudentDto();
  }

  @RequestMapping("/allGroups")
  public String getAllGroups(Model model){
    model.addAttribute("groups", groupService.getAllGroupsDto());
    return "allGroups.html";
  }
}
