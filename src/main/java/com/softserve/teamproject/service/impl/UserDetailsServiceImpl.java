package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Role;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.MessageByLocaleService;
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
 * The class implements UserDetailsService interface and overrides it's method loadUserByUsername.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;
  private MessageByLocaleService messageByLocaleService;

  @Autowired
  public void setMessageByLocaleService(
      MessageByLocaleService messageByLocaleService) {
    this.messageByLocaleService = messageByLocaleService;
  }

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
   * @throws UsernameNotFoundException if there is no such user in the database
   */
  @Override
  public UserDetails loadUserByUsername(String userName) throws
      UsernameNotFoundException {
    User user = userRepository.getUserByNickName(userName);
    if (user == null) {
      throw new UsernameNotFoundException(
          messageByLocaleService.getMessage("username.notFound.userDetailService", userName)
      );//"User " + userName + " is not found."
    }
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    Role role = user.getRole();
    grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

    return new org.springframework.security.core.userdetails.User(user
        .getNickName(), user.getPassword(), grantedAuthorities);
  }
}
