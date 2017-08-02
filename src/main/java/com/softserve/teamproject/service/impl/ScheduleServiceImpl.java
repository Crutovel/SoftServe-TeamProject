package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.repository.expression.EventExpressions.getKeyDates;

import com.softserve.teamproject.dto.EventResponseWrapper;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.entity.assembler.EventResourceAssembler;
import com.softserve.teamproject.entity.resource.EventResource;
import com.softserve.teamproject.repository.EventRepository;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.repository.custom.EventRepositoryCustom;
import com.softserve.teamproject.service.KeyDatesValidator;
import com.softserve.teamproject.service.ScheduleService;
import com.softserve.teamproject.validation.SimpleEventValidator;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private EventRepository eventRepository;
  private GroupRepository groupRepository;
  private KeyDatesValidator keyDatesValidator;
  private UserRepository userRepository;
  private SimpleEventValidator eventValidator;
  private EventResourceAssembler eventResourceAssembler;


  @Autowired
  public void setEventResourceAssembler(
      EventResourceAssembler eventResourceAssembler) {
    this.eventResourceAssembler = eventResourceAssembler;
  }

  @Autowired
  public void setEventValidator(SimpleEventValidator eventValidator) {
    this.eventValidator = eventValidator;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setGroupRepository(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  public EventRepositoryCustom getEventRepository() {
    return eventRepository;
  }

  @Autowired
  public void setKeyDatesValidator(KeyDatesValidator keyDatesValidator) {
    this.keyDatesValidator = keyDatesValidator;
  }


  @Autowired
  public void setEventRepository(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public Iterable<EventResource> getKeyEventsByGroupId(Integer groupId) {
    return convertToResource(eventRepository.getKeyEventsByGroupId(groupId));
  }

  public Iterable<EventResource> getAllEvents() {
    return convertToResource(eventRepository.findAll());
  }

  public Iterable<EventResource> getAllKeyEvents() {
    return convertToResource(eventRepository.findAll(getKeyDates()));
  }

  private Iterable<EventResource> getLastWeekEvents(Integer groupId) {
    Group group = groupRepository.findOne(groupId);
    TemporalField temporalField = WeekFields.of(Locale.US).dayOfWeek();
    LocalDate start;
    LocalDate end;
    if (group.getStatus().getStatusCategory().getName().equals("current")) {
      start = LocalDate.now().with(temporalField, 1);
      end = LocalDate.now().with(temporalField, 7);
    } else if (group.getStatus().getStatusCategory().getName().equals("finished")) {
      LocalDate finished = group.getFinishDate();
      start = finished.with(temporalField, 1);
      end = finished.with(temporalField, 7);
    } else {
      return null;
    }
    return getEventsByGroupId(groupId, start, end);
  }

  public Iterable<EventResource> getEventsByGroupId(Integer groupId, LocalDate start,
      LocalDate end) {
    if (start == null && end == null) {
      return getLastWeekEvents(groupId);
    }
    if (start == null || end == null) {
      throw new IllegalArgumentException("Bad request");
    }
    return convertToResource(eventRepository.getEventsByGroupId(
        groupId, start.atStartOfDay(), end.plusDays(1).atStartOfDay()));
  }

  public Iterable<EventResource> getEventsByFilter(
      Integer[] groupId, LocalDate start, LocalDate end) {
    if (start == null || end == null) {
      throw new IllegalArgumentException("Dates must be specified");
    }
    return convertToResource(eventRepository.getEventsByGroupId(
        groupId, start.atStartOfDay(), end.plusDays(1).atStartOfDay()));
  }

  public Iterable<EventResource> getKeyEventsByFilter(Integer[] groupId) {
    return convertToResource(eventRepository.getKeyEventsByGroupId(groupId));
  }

  public EventResource getEvent(Integer id) {
    return eventResourceAssembler.toResource(eventRepository.findOne(id));
  }

  private void addSingleEvent(Event event, Principal principal)
      throws AccessDeniedException, ValidationException {
    User user = userRepository.getUserByNickName(principal.getName());
    if (!event.getGroup().getLocation().equals(user.getLocation())) {
      throw new AccessDeniedException(
          ": The coordinators can create the schedule only in their location");
    } else {
      eventValidator.isEventValid(event);
      eventRepository.save(event);
    }
  }

  @Override
  public List<EventResource> addSchedule(List<Event> events, Integer groupId, Principal principal)
      throws AccessDeniedException, ValidationException {
    Group group = groupRepository.findOne(groupId);
    List<EventResource> eventResourceList = new ArrayList<>();
    for (Event event : events) {
      event.setGroup(group);
      addSingleEvent(event, principal);
      EventResource eventResource = eventResourceAssembler.toResource(event);
      eventResourceList.add(eventResource);
    }
    return eventResourceList;
  }

  private Event updateSingleEvent(Event event, Principal principal)
      throws AccessDeniedException, ValidationException {
    User user = userRepository.getUserByNickName(principal.getName());
    if (!event.getGroup().getLocation().equals(user.getLocation())) {
      throw new AccessDeniedException(
          ": The coordinators can edit the schedule only in their location");
    } else {
      if (event.getId() == 0) {
        throw new ValidationException("Please specify the event you are going to change.");
      }
      Event eventToUpdate = checkEventFields(event);
      eventRepository.save(eventToUpdate);
      return eventToUpdate;
    }
  }

  private Event checkEventFields(Event event) {
    Event eventToUpdate = eventRepository.findOne(event.getId());
    LocalDateTime oldLocalDateTime = eventToUpdate.getDateTime();
    if (!(event.getDateTime() == null)) {
      eventToUpdate.setDateTime(event.getDateTime());
    }
    if (!(event.getDuration() == 0)) {
      eventToUpdate.setDuration(event.getDuration());
    }
    if (!(event.getEventType() == null)) {
      eventToUpdate.setEventType(event.getEventType());
    }
    if (!(event.getRoom() == null)) {
      eventToUpdate.setRoom(event.getRoom());
    }
    eventValidator.isEventUpdateValid(eventToUpdate, oldLocalDateTime);
    return eventToUpdate;
  }

  @Override
  public List<EventResource> updateSchedule(List<Event> events, Integer groupId,
      Principal principal)
      throws AccessDeniedException, ValidationException {
    Group group = groupRepository.findOne(groupId);
    List<EventResource> eventResourceList = new ArrayList<>();
    for (Event event : events) {
      event.setGroup(group);
      event = updateSingleEvent(event, principal);
      EventResource eventResource = eventResourceAssembler.toResource(event);
      eventResourceList.add(eventResource);
    }
    return eventResourceList;
  }

  /**
   * Add or update key date to specified group
   *
   * @param events list of key dates to add or update
   * @param groupId id of group to update
   * @return <code>EventResponseWrapper</code> that contains info about successful updating and
   * invalid events
   */
  public EventResponseWrapper addKeyDates(List<Event> events, Integer groupId) {
    Group group = groupRepository.findOne(groupId);
    if (group == null) {
      throw new IllegalArgumentException("Group doesn't exist");
    }
    Map<EventType, LocalDate> validDates = keyDatesValidator.generateDates(group);
    List<EventResource> succeed = new ArrayList<>();
    Map<Event, String> invalidEvents = new HashMap<>();
    for (Event event : events) {
      try {
        event.setGroup(group);
        keyDatesValidator.validateKeyDate(event, validDates);
        Event existedEvent = eventRepository.getEventByEventTypeId(
            event.getEventType().getId(), group.getId());
        if (existedEvent != null) {
          event.setId(existedEvent.getId());
        }
        eventRepository.save(event);
        succeed.add(eventResourceAssembler.toResource(event));

      } catch (IllegalArgumentException ex) {
        invalidEvents.put(event, ex.getMessage());
      }
    }
    return new EventResponseWrapper(succeed, invalidEvents);
  }

  private Iterable<EventResource> convertToResource(Iterable<Event> events) {
    List<EventResource> eventResources = new ArrayList<>();
    events.forEach(event -> eventResources.add(eventResourceAssembler.toResource(event)));
    return eventResources;
  }
}
