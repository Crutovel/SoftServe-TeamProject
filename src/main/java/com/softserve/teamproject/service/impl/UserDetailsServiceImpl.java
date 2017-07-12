package com.softserve.teamproject.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The class implements UserDetailsService interface and overrides its loadUserByUsername(String
 * userName) method.
 */
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
   *
   * @param userName String value
   * @return UserDetails with user permission
   * @throws UsernameNotFoundException is thrown when there is no user with the corresponding user
   * name.
   */
  @Override
  public UserDetails loadUserByUsername(String userName) throws
      UsernameNotFoundException {
    User user = userRepository.getUserByNickName(userName);
    if (user == null) {
      throw new UsernameNotFoundException("User " + userName + " is not found.");
    }
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    Role role = user.getRole();
    grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

    return new org.springframework.security.core.userdetails.User(user
        .getNickName(), user.getPassword(), grantedAuthorities);
  }
}
