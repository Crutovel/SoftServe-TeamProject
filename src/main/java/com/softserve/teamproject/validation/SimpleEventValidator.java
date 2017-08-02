package com.softserve.teamproject.validation;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.repository.EventTypeRepository;
import com.softserve.teamproject.repository.RoomRepository;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The class provides methods for basic validation of the created or updated event.
 */
@Component
public class SimpleEventValidator {

  private RoomRepository roomRepository;
  private EventTypeRepository eventTypeRepository;

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
  public void isEventValid(Event event) throws ValidationException {
    Class<?> eventClass = event.getClass();
    for (Field field : eventClass.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (field.get(event) == null) {
          throw new ValidationException(
              "Please, provide all the information in fields to create an event.");
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    if (!doesRoomExist(event.getRoom())) {
      throw new ValidationException("The room doesn't exist");
    }
    if (isEventDateValid(event.getDateTime())) {
      throw new ValidationException("You cannot set the event time retroactively.");
    }
  }

  /**
   * Method checks whether the fields are valid and whether the update date is not before the date
   * set for this event.
   *
   * @param event of the Event type
   * @param oldDate which is used to check whether the update date is not before the set date.
   */
  public void isEventUpdateValid(Event event, LocalDateTime oldDate) throws ValidationException {
    if (!isEventTypeValid(event.getEventType())) {
      throw new ValidationException("The event type doesn't exist.");
    }
    if (!doesRoomExist(event.getRoom())) {
      throw new ValidationException("The room doesn't exist.");
    }
    if (!isEventUpdateDateValid(oldDate, event.getDateTime())) {
      throw new ValidationException("You cannot set the event time retroactively.");
    }
  }

  private boolean doesRoomExist(Room room) {
    if (roomRepository.findOne(room.getId()) == null) {
      return false;
    } else {
      return true;
    }
  }

  private boolean isEventTypeValid(EventType eventType) {
    if (eventTypeRepository.findOne(eventType.getId()) == null) {
      return false;
    } else {
      return true;
    }
  }

  private boolean isEventDateValid(LocalDateTime dateTime) {
    LocalDateTime currentTime = LocalDateTime.now();
    if (dateTime.isBefore(currentTime)) {
      return false;
    }
    return true;
  }

  private boolean isEventUpdateDateValid(LocalDateTime oldDateTime, LocalDateTime newDateTime) {
    if (newDateTime.isBefore(oldDateTime)) {
      return false;
    }
    return true;
  }


}

