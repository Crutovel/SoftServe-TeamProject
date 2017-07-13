package com.softserve.temproject.service;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import com.softserve.teamproject.entity.Role;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.impl.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImplTest {

  @Mock
  UserRepository userRepository;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsername_UserDoesNotExist_ExceptionThrown() {
    when(userRepository.getUserByNickName(anyString())).thenReturn(null);
    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
    userDetailsService.setUserRepository(userRepository);
    userDetailsService.loadUserByUsername("");
  }

  @Test
  public void loadUserByUsername_UserExist_UserReturned() {
    User user = new User();
    user.setNickName("Test");
    user.setPassword("1234");
    Role role = new Role();
    role.setName("Role");
    user.setRole(role);
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
    when(userRepository.getUserByNickName(anyString())).thenReturn(user);

    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
    userDetailsService.setUserRepository(userRepository);
    UserDetails actual = userDetailsService.loadUserByUsername("");

    Assert.assertEquals(actual.getUsername(), user.getNickName());
    Assert.assertEquals(actual.getPassword(), actual.getPassword());
    Assert.assertTrue(actual.getAuthorities().contains(authority));
  }
}
