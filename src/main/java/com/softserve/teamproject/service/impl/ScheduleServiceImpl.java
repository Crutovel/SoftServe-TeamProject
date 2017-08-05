package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.repository.expression.EventExpressions.getKeyDates;
import static com.softserve.teamproject.utils.DateUtil.getMondayDateOfWeek;
import static com.softserve.teamproject.utils.DateUtil.getSundayDateOfWeek;

import com.softserve.teamproject.dto.CopyPasteScheduleWrapper;
import com.softserve.teamproject.dto.EventDto;
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
import com.softserve.teamproject.utils.DateUtil;
import com.softserve.teamproject.validation.SimpleEventValidator;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.PersistenceContext;
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

  private void validateEvent(Event event, Principal principal)
      throws AccessDeniedException, ValidationException {
    User user = userRepository.getUserByNickName(principal.getName());
    if (!event.getGroup().getLocation().equals(user.getLocation())) {
      throw new AccessDeniedException(
          ": The coordinators can create the schedule only in their location");
    } else {
      eventValidator.isEventValid(event);
    }
  }

  @Override
  public List<EventResource> addSchedule(List<Event> events, Integer groupId, Principal principal)
      throws AccessDeniedException, ValidationException {
    Group group = groupRepository.findOne(groupId);
    for (Event event : events) {
      event.setGroup(group);
      validateEvent(event, principal);
    }
    eventRepository.save(events);
    return events.stream().map(eventResourceAssembler::toResource).collect(
        Collectors.toList());
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
    if (eventToUpdate == null) {
      throw new ValidationException("The event doesn't exist.");
    }
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

  private void prepareEventsForCopy(List<Event> copyWeekEvents, LocalDate start) {
    long diff = ChronoUnit.DAYS.between(DateUtil.getMondayDateOfWeek(start),
        DateUtil.getMondayDateOfWeek(copyWeekEvents.get(0).getDateTime().toLocalDate()));
    if (diff > 0) {
      for (Event event : copyWeekEvents) {
        event.setDateTime(event.getDateTime().plusDays(diff));
      }
    }
    if (diff < 0) {
      for (Event event : copyWeekEvents) {
        event.setDateTime(event.getDateTime().minusDays(diff));
      }
    }
  }

  private void generateEventsForPaste(List<Event> copyWeekEvents, LocalDate start,
      LocalDate finish, List<Event> correct, List<Event> incorrect) {
    prepareEventsForCopy(copyWeekEvents, start);
    LocalDateTime eventDateTime;
    Event temp;
    for (Event event : copyWeekEvents) {
      for (int i = 0; ; i++) {
        eventDateTime = event.getDateTime().plusDays(7 * i);

        if (eventDateTime.isBefore(start.atStartOfDay())) {
          continue;
        }
        if (eventDateTime.isAfter(finish.plusDays(1).atStartOfDay())) {
          break;
        }
        temp = new Event(eventDateTime, event.getDuration(),
            event.getRoom(), event.getGroup(), event.getEventType());
        if (isEventConflicts(temp)) {
          incorrect.add(temp);
        } else {
          correct.add(temp);
        }
      }
    }
  }

  private List<Event> getEventsForCopy(Integer groupId, LocalDate copyWeekDate) {
    LocalDateTime start = getMondayDateOfWeek(copyWeekDate).atStartOfDay();
    LocalDateTime end = getSundayDateOfWeek(copyWeekDate).plusDays(1).atStartOfDay();
    List<Event> events = eventRepository.getNotKeyEventsByGroupId(groupId, start, end);
    List<Event> copyEvents = new ArrayList<>();
    events.forEach(event -> copyEvents.add(new Event(event)));
    return copyEvents;
  }

  /**
   * Copy & Paste events to schedule by weeks.
   *
   * @param copyPasteSchedule wrapper which contains info such as group id, copy date, paste date
   * @return list of conflict events
   */
  @Override
  public Iterable<EventDto> copyPasteSchedule(CopyPasteScheduleWrapper copyPasteSchedule) {
    Group group = copyPasteSchedule.getGroup();
    List<Event> correct = new ArrayList<>();
    List<Event> incorrect = new ArrayList<>();
    LocalDate copyWeekDate = copyPasteSchedule.getCopyWeekDate();
    LocalDate pasteWeekDate = copyPasteSchedule.getPasteWeekDate();
    LocalDate pasteEnd = copyPasteSchedule.getPasteFillDate();
    List<Event> copyEvents = getEventsForCopy(group.getId(), copyWeekDate);
    if (copyEvents.size() == 0) {
      throw new IllegalArgumentException("No events for copy");
    }
    LocalDate start;
    LocalDate end;

    if (pasteWeekDate != null) {
      start = getMondayDateOfWeek(pasteWeekDate);
      end = getSundayDateOfWeek(pasteWeekDate);
    } else {
      if (copyWeekDate.isBefore(LocalDate.now())) {
        start = LocalDate.now();
      } else {
        start = copyWeekDate;
      }
      end = pasteEnd;
    }
    generateEventsForPaste(copyEvents, start, end, correct, incorrect);
    eventRepository.save(correct);
    List<EventDto> eventDtos = new ArrayList<>();
    incorrect.forEach(event -> eventDtos.add(new EventDto(event)));
    Collections.sort(eventDtos);
    return eventDtos;
  }

  private Integer[] getGroupIdsByLocation(Event event) {
    List<Group> groups = groupRepository
        .getGroupsByLocationId(event.getGroup().getLocation().getId());
    Integer[] groupIds = new Integer[groups.size()];
    int i = 0;
    for (Group item : groups) {
      groupIds[i] = item.getId();
      i++;
    }
    return groupIds;
  }

  /**
   * Check event for conflict.
   *
   * @param event that we check
   * @return if conflicts true, if not false
   */
  private boolean isEventConflicts(Event event) {
    List<Event> events = eventRepository.getEventsByGroupId(getGroupIdsByLocation(event),
        event.getDateTime().toLocalDate().atStartOfDay(),
        event.getDateTime().plusDays(1).toLocalDate().atStartOfDay());
    for (Event item : events) {
      if (event.getDateTime().plusMinutes(event.getDuration()).isAfter(item.getDateTime())
          && event.getDateTime().isBefore(item.getDateTime().plusMinutes(item.getDuration()))) {
        return true;
      }
    }
    return false;
  }

}
