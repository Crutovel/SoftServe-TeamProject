package com.softserve.teamproject.service;

import com.softserve.teamproject.dto.UserDto;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Use for operation with User entity
 */
public interface UserService {

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  public List<UserDto> getAllUserDto();

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  public void deleteUser(int userId);

  @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
  public UserDto findUser(int userId);
}
