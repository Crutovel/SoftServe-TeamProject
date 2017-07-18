package com.softserve.teamproject.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import java.util.StringTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Retrieve User from database by it Id when Spring parse JSON field which contains user id
 */
@Component
public class UserFromIdConverter implements Converter<String, User> {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieve User from database by it Id
   * @param source JSON field which contains user id
   * @return User from database
   */
  @Override
  public User convert(String source) {
    return userRepository.findOne(Integer.valueOf(source));
  }
}
