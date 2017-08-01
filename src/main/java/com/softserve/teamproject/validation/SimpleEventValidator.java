package com.softserve.teamproject.validation;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.repository.RoomRepository;
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

  @Autowired
  public void setRoomRepository(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  /**
   * Method checks whether the room with the specified id exists and whether the time for the event
   * is valid.
   *
   * @param event of the Event type
   * @throws ValidationException if one of the conditions is violated
   */
  public void isEventValid(Event event) throws ValidationException {
    if (!doesRoomExist(event.getRoom())) {
      throw new ValidationException("The room doesn't exist");
    }
    if (!isEventDateValid(event.getDateTime())) {
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

  private boolean isEventDateValid(LocalDateTime dateTime) {
    LocalDateTime currentTime = LocalDateTime.now();
    if (dateTime.isBefore(currentTime)) {
      return false;
    }
    return true;
  }


}

