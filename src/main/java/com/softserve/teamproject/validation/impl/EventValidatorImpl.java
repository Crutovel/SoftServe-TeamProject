package com.softserve.teamproject.validation.impl;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.EventRepository;
import com.softserve.teamproject.repository.EventTypeRepository;
import com.softserve.teamproject.repository.RoomRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.validation.EventValidator;
import java.lang.reflect.Field;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
      invalidField.setName("dateTime = "+event.getStart());
      throw new ValidationException("You cannot set the event time retroactively.");
    }
    if (!isEventTypeValid(event.getEventType())) {
      invalidField.setName("eventType");
      throw new ValidationException("The event type doesn't exist or incorrect.");
    }
    if (!isEventInFreeRoom(event)) {
      invalidField.setName("room id=" + event.getRoom().getId());
      throw new ValidationException(
          "The room " + event.getRoom().getNumber() + " in " + event.getRoom().getLocation()
              .getName() + " has already been taken for another event on " + DateTimeFormatter
              .ofPattern("yyyy-MM-dd HH:mm").format(event.getStart()));
    }
  }

  /**
   * Method checks whether the coordinator tries to make changes in schedule for his location.
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
        if (field.get(event) == null&&!(field.getName().equals("id"))) {
          invalidField.setName(field.getName());
          throw new ValidationException(
              "Please, provide the relevant information for the field -" + field.getName()
                  + "- to create an event.");
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
  public void isEventUpdateValid(Event event, Principal principal, InvalidField invalidField) throws ValidationException {
    User user = userRepository.getUserByNickName(principal.getName());
    if (!event.getGroup().getLocation().equals(user.getLocation())) {
      invalidField.setName("location");
      throw new AccessDeniedException(
          ": The coordinators can create the schedule only in their location");
    }
    if (event.getId()==null) {
      invalidField.setName("eventId");
      throw new ValidationException("Please specify the event you are going to change.");
    }
    if (!isEventTypeValid(event.getEventType())) {
      invalidField.setName("eventType");
      throw new ValidationException("The event type doesn't exist or inappropriate.");
    }
    if (!doesRoomExist(event.getRoom())) {
      invalidField.setName("room id=" + event.getRoom().getId());
      throw new ValidationException("The room doesn't exist.");
    }
    if (!isUpdateEventInFreeRoom(event)) {
      invalidField.setName("room id=" + event.getRoom().getId());
      throw new ValidationException(
          "The room " + event.getRoom().getNumber() + " in " + event.getRoom().getLocation()
              .getName() + " has already been taken for another event on " + DateTimeFormatter
              .ofPattern("yyyy-MM-dd HH:mm").format(event.getStart()));
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
    LocalDateTime startTime = event.getStart();
    LocalDateTime endTime = event.getEnd();
    List<Event> crossEvents = eventRepository.getCrossEvents(startTime, endTime);
    if (crossEvents.isEmpty()) {
      return true;
    } else {
      for (Event e : crossEvents) {
        if (event.getRoom().equals(e.getRoom())) {
          return false;
        }
      }
      return true;
    }
  }

  public boolean isUpdateEventInFreeRoom(Event event) {
    LocalDateTime startTime = event.getStart();
    LocalDateTime endTime = event.getEnd();
    List<Event> crossEvents = eventRepository.getCrossEvents(startTime, endTime);
    if (crossEvents.isEmpty()) {
      return true;
    } else {
      for (Event e : crossEvents) {
        if (event.getRoom().equals(e.getRoom()) && !(event.getId().equals(e.getId()))) {
          return false;
        }
      }
      return true;
    }
  }
}


