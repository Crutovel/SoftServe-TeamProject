package com.softserve.teamproject.validation;

import com.softserve.teamproject.dto.CopyPasteScheduleWrapper;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import java.security.Principal;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;


@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class CopyPasteScheduleValidator implements
    ConstraintValidator<ValidCopyPasteSchedule, Object[]> {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void initialize(ValidCopyPasteSchedule constraint) {
  }

  private void validateGroup(Group group, User user) {
    if (user.getRole().getRoleCategory().getName().equals("itacademy")
        && !group.getTeachers().contains(user)) {
      throw new AccessDeniedException(
          "teacher can copy/paste the schedule only for their groups");
    }
    if (user.getRole().getName().equals("coordinator") && !group.getLocation()
        .equals(user.getLocation())) {
      throw new AccessDeniedException(
          "coordinator can create the schedule only in their location");
    }
    if (group.getStatus().getStatusCategory().getName().equals("future")) {
      throw new IllegalArgumentException("group not started yet");
    }
    if (group.getStatus().getStatusCategory().getName().equals("finished")) {
      throw new IllegalArgumentException("group already finished");
    }
  }

  private void validatePasteDate(LocalDate pasteDate, LocalDate today, LocalDate startDate,
      LocalDate finishDate) {
    if (pasteDate.isBefore(startDate)) {
      throw new IllegalArgumentException("You cannot paste events before group start date.");
    }
    if (pasteDate.isBefore(today)) {
      throw new IllegalArgumentException("You cannot paste events retroactively.");
    }
    if (pasteDate.isAfter(finishDate)) {
      throw new IllegalArgumentException("You cant paste events after group finish date.");
    }
  }

  private void validateCopyDate(LocalDate copyDate, LocalDate startDate,
      LocalDate finishDate) {
    if (copyDate.isBefore(startDate)) {
      throw new IllegalArgumentException("You cannot copy events before group start date");
    }
    if (copyDate.isAfter(finishDate)) {
      throw new IllegalArgumentException("You cant copy events after group finish date");
    }
  }

  private void validateDates(LocalDate copyWeekDate, LocalDate pasteWeekDate,
      LocalDate pasteFillDate,
      Group group) {
    if ((pasteWeekDate == null && pasteFillDate == null)
        || (pasteWeekDate != null && pasteFillDate != null)) {
      throw new IllegalArgumentException(
          "There must be one parameter: pasteWeekDate or pasteFillDate");
    }
    LocalDate startDate = group.getStartDate();
    LocalDate finishDate = group.getFinishDate();
    LocalDate today = LocalDate.now();
    LocalDate pasteDate;
    if (pasteWeekDate != null) {
      pasteDate = pasteWeekDate;
    } else {
      pasteDate = pasteFillDate;
    }
    validatePasteDate(pasteDate, today, startDate, finishDate);
    validateCopyDate(copyWeekDate, startDate, finishDate);
  }

  @Override
  public boolean isValid(Object[] value, ConstraintValidatorContext context) {
    if (value.length != 2 || !(value[0] instanceof CopyPasteScheduleWrapper)
        || !(value[1] instanceof Principal)) {
      throw new IllegalArgumentException("Illegal method signature");
    }
    CopyPasteScheduleWrapper wrapper = (CopyPasteScheduleWrapper) value[0];
    Group group = wrapper.getGroup();
    User user = userRepository.getUserByNickName(((Principal) value[1]).getName());
    try {
      validateGroup(group, user);
      validateDates(wrapper.getCopyWeekDate(), wrapper.getPasteWeekDate(), wrapper.getPasteFillDate(), group);
      return true;
    } catch (AccessDeniedException | IllegalArgumentException e) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(e.getMessage()).addConstraintViolation();
      return false;
    }
  }
}
