package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.User;

public interface UserRepositoryCustom {

  User getUserByNickName(String nickName);

}
