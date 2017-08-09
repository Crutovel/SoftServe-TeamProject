package com.softserve.teamproject.validation.impl;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.validation.GroupValidator;
import java.lang.reflect.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class GroupValidatorImpl implements GroupValidator {

  private GroupRepository groupRepository;

  @Autowired
  public void setGroupRepository(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  /**
   * Checks if all fields of updated group exist. If some field is not exists, we copy required
   * fields from an old group using reflection.
   *
   * @param group is a group we want to update.
   */
  @Override
  public void fieldsCheck(Group group) {
    Group existedGroup = groupRepository.findOne(group.getId());
    Class<?> groupClass = group.getClass();
    for (Field field : groupClass.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (field.get(group) == null) {
          Field existedField = existedGroup.getClass().getDeclaredField(field.getName());
          existedField.setAccessible(true);
          field.set(group, existedField.get(existedGroup));
        }
      } catch (IllegalAccessException | NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Checks if group is valid. If group already exists, group is not valid. Method is used by
   * addGroup method.
   *
   * @param group is a group we want to add.
   * @return true if group is valid, or false if group is not valid.
   */
  @Override
  public boolean isValid(Group group) {
    Group existed = groupRepository.findByName(group.getName());
    return existed == null;
  }


  /**
   * Checks if group's name is valid. Name is valid if the other groups don't have the same name.
   * Method is used by updateGroup method.
   *
   * @param group is a group we want to update.
   * @return true if group's name is valid, or false if group's name is not valid.
   */
  @Override
  public boolean isValidGroupName(Group group) {
    Group currentGroup = groupRepository.findByName(group.getName());
    if (currentGroup == null) {
      return true;
    }
    if (currentGroup.getName().equals(group.getName()) && currentGroup.getId() != group.getId()) {
      return false;
    }
    return true;
  }

  @Override
  public void checkCoordinatorLocationToManipulateGroup(User user, Group group) {
    if (user.getRole().getName().equals("coordinator")
        && !user.getLocation().equals(group.getLocation())) {
      throw new AccessDeniedException("Coordinator can't delete group in alien location");
    }
  }

  @Override
  public void checkGroupEditPermissions(User user, Group group, Status currentStatus){
    if (user.getRole().getName().equals("teacher")) {
      if (group.getTeachers().contains(user) && !user.getLocation().equals(group.getLocation())) {
        throw new AccessDeniedException("Teacher can't edit group in alien location");
      } else if (!group.getTeachers().contains(user)) {
        throw new AccessDeniedException("Teacher can't edit group which is not assigned to him.");
      } else if (group.getTeachers().contains(user)
          && user.getLocation().equals(group.getLocation()) && currentStatus
          .getName().equalsIgnoreCase("graduated")) {
        throw new AccessDeniedException("Teacher can't edit group which is graduated.");
      }
    } else if (user.getRole().getName().equals("coordinator")
        && !user.getLocation().equals(group.getLocation())) {
      throw new AccessDeniedException("Coordinator can't edit group in alien location");
    }
  }
}
