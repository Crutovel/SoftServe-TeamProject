package com.softserve.teamproject.validation;

import com.softserve.teamproject.dto.KeyDateDto;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Template;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.SecurityService;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class made to generate valid key dates according to generation strategy and validate incoming
 * events
 */
@Service
public class KeyDatesValidator implements
    ConstraintValidator<KeyDates, KeyDateDto> {

  private UserRepository userRepository;
  private SecurityService securityService;
  private Group group;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setSecurityService(SecurityService securityService) {
    this.securityService = securityService;
  }

  @Override
  public void initialize(KeyDates constraintAnnotation) {
  }

  @Override
  public boolean isValid(KeyDateDto value, ConstraintValidatorContext context) {
    try {
      validateKeyDate(value);
      return true;
    } catch (IllegalArgumentException e) {
      setNewMessage(e.getMessage(), context);
      return false;
    }
  }

  private Map<EventType, LocalDate> validationTemplate = new HashMap<>();

  /**
   * Generate dates, which are pointing to demo week
   *
   * @param group the group to generate dates for
   */
  private void generateDates(Group group) {
    if (group.getStartDate() == null) {
      throw new IllegalArgumentException("Group start date must be specified");
    }
    LocalDate startDate = group.getStartDate();
    List<Template> templates = group.getSpecialization().getStrategy().getTemplates();
    for (Template template : templates) {
      LocalDate newDate = null;
      if (template.getDuration() == 0) {
        newDate = group.getFinishDate();
      } else {
        newDate = startDate.plusDays(template.getDuration());
      }

      validationTemplate.put(template.getEventType(), newDate);
      startDate = newDate;
    }
  }

  /**
   * Validates input event according to <code>validDates</code> parameter
   *
   * @param event event to validate
   * @throws IllegalArgumentException if event type is incorrect or date is invalid
   */
  private void validateKeyDate(KeyDateDto event) {
    updateTemplate(event);
    if (event.getEventType() == null || !validationTemplate.containsKey(event.getEventType())) {
      throw new IllegalArgumentException("Incorrect Event Type");
    }
    TemporalField temporalField = WeekFields.of(Locale.forLanguageTag("ru")).dayOfWeek();
    LocalDate startOfWeek = validationTemplate.get(event.getEventType()).with(temporalField, 1);
    LocalDate endOfWeek = startOfWeek.with(temporalField, 5);
    LocalDate eventDate = event.getDate();
    if (eventDate == null || !(eventDate.isAfter(startOfWeek) && eventDate.isBefore(endOfWeek))) {
      throw new IllegalArgumentException("Wrong date specified");
    }
  }

  private void updateTemplate(KeyDateDto event) {
    if (event.getGroup() == null) {
      throw new IllegalArgumentException("Group is not specified");
    }
    checkAuth(event.getGroup());
    if (group == null || !event.getGroup().equals(group)) {
      generateDates(event.getGroup());
      group = event.getGroup();
    }
  }

  private void setNewMessage(String message, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
  }

  private void checkAuth(Group group) {
    User currentUser = userRepository.getUserByNickName(securityService.findLoggedInUsername());
    if ((currentUser.getRole().getName().equals("coordinator")
        && currentUser.getLocation() != group.getLocation())
        || (currentUser.getRole().getName().equals("teacher")
        && !group.getTeachers().contains(currentUser))) {
      throw new IllegalArgumentException("Access denied");
    }
  }
}
