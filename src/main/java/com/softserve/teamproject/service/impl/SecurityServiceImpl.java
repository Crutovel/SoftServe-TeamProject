package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * The class implements SecurityService interface and provides methods to find the logged-in user
 * name and auto log in a user after the registration.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * Method returns a name of the logged in user. We can use it to say hello to the user.
   *
   * @return String userName
   */
  @Override
  public String findLoggedInUsername() {
    Object user = SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    if (user instanceof User) {
      return ((User) user).getUsername();
    }

    return null;
  }

  /**
   * The method is used to log in a user automatically after the registration.
   *
   * @param username String value
   * @param password String value
   */
  @Override
  public void autoLogin(String username, String password) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails,
            password, userDetails.getAuthorities());

    authenticationManager.authenticate(authenticationToken);

    if (authenticationToken.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
  }

}
