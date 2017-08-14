package com.softserve.teamproject.validation;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.User;

public interface GroupValidator {

  void fieldsCheck(Group group);

  boolean isValid(Group group);

  boolean isValidGroupName(Group group);

  void checkCoordinatorLocationToManipulateGroup(User user, Group group);

  void checkGroupEditPermissions(User user, Group group, Status currentStatus);
}
