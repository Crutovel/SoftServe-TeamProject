package com.softserve.teamproject.validation;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.validation.impl.InvalidField;
import java.security.Principal;
import javax.validation.ValidationException;
import org.springframework.security.access.AccessDeniedException;

public interface EventValidator {

  void isEventValid(Event event, Principal principal, InvalidField invalidField)
      throws AccessDeniedException, ValidationException;

  void isEventUpdateValid(Event event, Principal principal, InvalidField invalidField) throws ValidationException;

  Event checkEventFields(Event event, Event existedEvent);

}
