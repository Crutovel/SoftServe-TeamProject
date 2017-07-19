package com.softserve.teamproject.validation;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.validation.UniqueGroup;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Use to check if group name is unique when new group is created
 */
public class UniqueGroupValidator implements ConstraintValidator<UniqueGroup, Object> {

  private GroupRepository groupRepository;

  @Autowired
  public void setGroupRepository(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  public void initialize(UniqueGroup constraint) {
  }

  /**
   * check if Group with some name is exist in database
   * @param value name of group to check
   * @param context validation context
   * @return true if group with given name is not exist in database
   */
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    Group existed = groupRepository.findByName(value.toString());
    return existed == null;
  }

}
