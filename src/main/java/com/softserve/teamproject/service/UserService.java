package com.softserve.teamproject.service;

import com.softserve.teamproject.dto.EditUserDto;
import com.softserve.teamproject.dto.UserDto;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Use for operation with User entity
 */
public interface UserService {

  @PreAuthorize("hasAnyAuthority('tes')")
  public List<UserDto> getAllUserDto();

  @PreAuthorize("hasAnyAuthority('tes')")
  public void deleteUser(int userId);

  @PreAuthorize("hasAnyAuthority('tes')")
  public EditUserDto findUser(int userId);

  @PreAuthorize("hasAnyAuthority('tes')")
  public void saveUser(UserDto userDto);
}
