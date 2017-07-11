package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Role;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetailsServiceImpl() {
  }

  /**
   * This is an obligatory method that implements the similar method of the
   * UserDetailsService interface. Here we get a user from the repository by
   * user's name, then get his role and then we return a value of UserDetails type,
   * which is actually provided by the framework itself. It's basically the
   * procedure of user authorization.
   */
  @Override
  public UserDetails loadUserByUsername(String userName) throws
      UsernameNotFoundException {
    User user = userRepository.getUserByNickName(userName);
    if (user == null) {
      throw new UsernameNotFoundException("User " + userName + " is not found.");
    }

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    //Role role = user.getRole();
    grantedAuthorities.add(new SimpleGrantedAuthority("Маманя"));

    return new org.springframework.security.core.userdetails.User(user
        .getNickName(), user.getPassword(), grantedAuthorities);
  }
}
