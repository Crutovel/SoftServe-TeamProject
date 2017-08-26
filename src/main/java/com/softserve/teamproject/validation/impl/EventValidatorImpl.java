package com.softserve.teamproject.validation.impl;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.EventRepository;
import com.softserve.teamproject.repository.EventTypeRepository;
import com.softserve.teamproject.repository.RoomRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.MessageByLocaleService;
import com.softserve.teamproject.validation.EventValidator;
import java.lang.reflect.Field;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 * The class provides methods for basic validation of the created or updated event.
 */
@Component
public class EventValidatorImpl implements EventValidator {

  private RoomRepository roomRepository;
  private EventTypeRepository eventTypeRepository;
  private EventRepository eventRepository;
  private UserRepository userRepository;
  private MessageByLocaleService messageByLocaleService;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setEventRepository(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Autowired
  public void setEventTypeRepository(EventTypeRepository eventTypeRepository) {
    this.eventTypeRepository = eventTypeRepository;
  }

  @Autowired
  public void setRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  @Autowired
  public void setMessageByLocaleService(
      MessageByLocaleService messageByLocaleService) {
    this.messageByLocaleService = messageByLocaleService;
  }

  /**
   * Method checks whether the room with the specified id exists and whether the time for the event
   * is valid and whether all the fields are present to create a valid event.
   *
   * @param event of the Event type
   * @throws ValidationException if one of the conditions is violated
   */
  public void isEventValid(Event event, Principal principal, InvalidField invalidField)
      throws AccessDeniedException, ValidationException {
    User user = userRepository.getUserByNickName(principal.getName());
    checkLocationPermission(event, user, invalidField);
    checkAllFields(event, invalidField);
    if (!isEventDateValid(event.getStart())) {
      invalidField.setName("startTime = " + event.getStart());
      throw new ValidationException(
          messageByLocaleService.getMessage("valid.schedule.edit.startTime.retroactive")
      );
    }
    if (!isEventTypeValid(event.getEventType())) {
      invalidField.setName("eventType");
      throw new ValidationException(
          messageByLocaleService.getMessage("valid.schedule.edit.eventType.incorrect")
      );
    }
    if (!isEventInFreeRoom(event)) {
      invalidField.setName("room id=" + event.getRoom().getId());
      throw new ValidationException(
          messageByLocaleService.getMessage("valid.schedule.edit.room.isBusy")
              + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(event.getStart()));
    }
  }

  /**
   * Method checks whether the coordinator tries to make changes in schedule for his location.
   *
   * @param event to be changed or added
   * @param user - a coordinator
   */
  public void checkLocationPermission(Event event, User user, InvalidField invalidField) {
    if (!event.getGroup().getLocation().equals(user.getLocation())) {
      invalidField.setName("location");
      throw new AccessDeniedException(
          ": The coordinators can create the schedule only in their location");
    }
  }


  /**
   * Method checks whether the income Event object has all the relevant fields.
   *
   * @param event to add to the database
   */
  public void checkAllFields(Event event, InvalidField invalidField) {
    Class<?> eventClass = event.getClass();
    for (Field field : eventClass.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (field.get(event) == null && !(field.getName().equals("id"))) {
          invalidField.setName(field.getName());
          throw new ValidationException(
              messageByLocaleService.getMessage("valid.checkAllFields")
                  + field.getName());
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Method checks whether the fields are valid and whether the update date is not before the date
   * set for this event.
   *
   * @param event of the Event type
   */
  public void isEventUpdateValid(Event event, Principal principal, InvalidField invalidField)
      throws ValidationException {
    User user = userRepository.getUserByNickName(principal.getName());
    if (!event.getGroup().getLocation().equals(user.getLocation())) {
      invalidField.setName("location");
      throw new AccessDeniedException(
          messageByLocaleService.getMessage("auth.schedule.create.coordinator.alienLocation")
      );
    }
    if (event.getId() == null) {
      invalidField.setName("eventId");
      throw new ValidationException(
          messageByLocaleService.getMessage("valid.schedule.edit.eventId.isNull")
      );
    }
    if (!isEventTypeValid(event.getEventType())) {
      invalidField.setName("eventType");
      throw new ValidationException(
          messageByLocaleService.getMessage("valid.schedule.edit.eventType.incorrect")
      );
    }
    if (!doesRoomExist(event.getRoom())) {
      invalidField.setName("room id=" + event.getRoom().getId());
      throw new ValidationException(
          messageByLocaleService.getMessage("valid.schedule.edit.room.notExist"));
    }
    if (!isUpdateEventInFreeRoom(event)) {
      invalidField.setName("room id=" + event.getRoom().getId());
      throw new ValidationException(
          messageByLocaleService.getMessage("valid.schedule.edit.room.isBusy")
              + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(event.getStart())
      );
    }
  }

  /**
   * Method checks the fields and sets the empty fields to the values of those present in the
   * database.
   *
   * @return event with all the fields ready to be updated
   */
  public Event checkEventFields(Event event, Event existedEvent) {
    Class<?> eventClass = event.getClass();
    for (Field field : eventClass.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (field.get(event) == null || field.get(event).equals(0)) {
          Field existedField = existedEvent.getClass().getDeclaredField(field.getName());
          existedField.setAccessible(true);
          field.set(event, existedField.get(existedEvent));
        }
      } catch (IllegalAccessException | NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
    return event;
  }

  private boolean doesRoomExist(Room room) {
    return !(roomRepository.findOne(room.getId()) == null);
  }


  private boolean isEventTypeValid(EventType eventType) {
    return !(eventTypeRepository.findOne(eventType.getId()) == null || eventType.isKeyDate());
  }

  private boolean isEventDateValid(LocalDateTime dateTime) {
    LocalDateTime currentTime = LocalDateTime.now();
    return !(dateTime.isBefore(currentTime));
  }

  public boolean isEventInFreeRoom(Event event) {
    return eventRepository.getCrossEvents(event.getStart(), event.getEnd(),
        event.getRoom().getId()) == null;
  }

  public boolean isUpdateEventInFreeRoom(Event event) {
    return eventRepository.getCrossEvents(event.getStart(), event.getEnd(),
        event.getRoom().getId(), event.getId()) == null;
  }
}


