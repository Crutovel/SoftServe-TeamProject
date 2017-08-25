package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.repository.expression.EventExpressions.getKeyDates;
import static com.softserve.teamproject.utils.DateUtil.getMondayDateOfWeek;
import static com.softserve.teamproject.utils.DateUtil.getSundayDateOfWeek;

import com.softserve.teamproject.dto.CopyPasteScheduleWrapper;
import com.softserve.teamproject.dto.EventDto;
import com.softserve.teamproject.dto.KeyDateDto;
import com.softserve.teamproject.dto.ScheduleResponseWrapper;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.assembler.EventResourceAssembler;
import com.softserve.teamproject.entity.resource.EventResource;
import com.softserve.teamproject.repository.EventRepository;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.service.MessageByLocaleService;
import com.softserve.teamproject.service.ScheduleService;
import com.softserve.teamproject.utils.DateUtil;
import com.softserve.teamproject.validation.EventValidator;
import com.softserve.teamproject.validation.impl.InvalidField;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
public class ScheduleServiceImpl implements ScheduleService {

  private EventRepository eventRepository;
  private GroupRepository groupRepository;
  private EventValidator eventValidator;
  private EventResourceAssembler eventResourceAssembler;
  private MessageByLocaleService messageByLocaleService;

  @Value("${group.current}")
  private String currentGroupStatus;
  @Value("${group.finished}")
  private String finishedGroupStatus;

  @Autowired
  public void setMessageByLocaleService(
      MessageByLocaleService messageByLocaleService) {
    this.messageByLocaleService = messageByLocaleService;
  }

  @Autowired
  public void setEventResourceAssembler(
      EventResourceAssembler eventResourceAssembler) {
    this.eventResourceAssembler = eventResourceAssembler;
  }

  @Autowired
  public void setEventValidator(EventValidator eventValidator) {
    this.eventValidator = eventValidator;
  }

  @Autowired
  public void setGroupRepository(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  @Autowired
  public void setEventRepository(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  public List<EventResource> getKeyEventsByGroupId(Integer groupId) {
    return convertToResource(eventRepository.getKeyEventsByGroupId(groupId));
  }

  public Iterable<EventResource> getAllEvents() {
    return convertToResource(eventRepository.findAll());
  }

  public Iterable<EventResource> getAllKeyEvents() {
    return convertToResource(eventRepository.findAll(getKeyDates()));
  }

  public List<EventResource> getLastWeekEvents(Integer groupId) {
    Group group = groupRepository.findOne(groupId);
    TemporalField temporalField = WeekFields.of(Locale.forLanguageTag("ru")).dayOfWeek();
    LocalDate start;
    LocalDate end;
    if (group.getStatus().getStatusCategory().getName().equals(currentGroupStatus)) {
      start = LocalDate.now().with(temporalField, 1);
      end = LocalDate.now().with(temporalField, 5);
    } else if (group.getStatus().getStatusCategory().getName().equals(finishedGroupStatus)) {
      LocalDate finished = group.getFinishDate();
      start = finished.with(temporalField, 1);
      end = finished.with(temporalField, 5);
    } else {
      throw new IllegalArgumentException(
          messageByLocaleService.getMessage("illegalArgs.schedule.group.plannedState"));
    }
    return getEventsByGroupId(groupId, start, end);
  }

  public List<EventResource> getEventsByGroupId(Integer groupId, LocalDate start,
      LocalDate end) {
    if (start == null && end == null) {
      return convertToResource(eventRepository.getEventsByGroupId(groupId));
    }
    if (start == null || end == null) {
      throw new IllegalArgumentException(
          messageByLocaleService.getMessage("illegalArgs.schedule.getEventsById"));
    }
    return convertToResource(eventRepository.getEventsByGroupId(
        groupId, start.atStartOfDay(), end.plusDays(1).atStartOfDay()));
  }

  public Iterable<EventResource> getEventsByFilter(
      List<Integer> groupId, LocalDate start, LocalDate end) {
    if (start == null || end == null) {
      throw new IllegalArgumentException(
          messageByLocaleService.getMessage("illegalArgs.schedule.getEventsByFilter"));
    }
    return convertToResource(eventRepository.getEventsByGroupId(
        groupId, start.atStartOfDay(), end.plusDays(1).atStartOfDay()));
  }

  public Iterable<EventResource> getKeyEventsByFilter(List<Integer> groupId) {
    return convertToResource(eventRepository.getKeyEventsByGroupId(groupId));
  }

  public EventResource getEvent(Integer id) {
    return eventResourceAssembler.toResource(eventRepository.findOne(id));
  }

  /**
   * The method adds a schedule (in fact, a list of events) in the database for the specified
   * group.
   *
   * @param events the list of events that form the schedule
   * @param groupId the id of the group
   * @param principal the authenticated user
   * @return eventResourceList - the list of elements of the EventResource type to display them in
   * hateoas style in json.
   * @throws AccessDeniedException when a coordinator wants to create a schedule in other location
   * @throws ValidationException when the  information is incorrect
   */
  @Override
  public ScheduleResponseWrapper addSchedule(List<Event> events, Integer groupId,
      Principal principal)
      throws AccessDeniedException, ValidationException {
    Group group = groupRepository.findOne(groupId);
    List<EventDto> invalidEvents = new ArrayList<>();
    List<Event> eventsToRemove = new ArrayList<>();
    processAllEventsBeforeAdd(events, invalidEvents, eventsToRemove, group, principal);
    events.removeAll(eventsToRemove);
    eventRepository.save(events);

    return new ScheduleResponseWrapper(
        events.stream().map(eventResourceAssembler::toResource).collect(Collectors.toList()),
        invalidEvents);
  }

  private void processAllEventsBeforeAdd(List<Event> events, List<EventDto> invalidEvents,
      List<Event> eventsToRemove, Group group, Principal principal) {
    Event event = new Event();
    EventDto eventDto;
    Iterator<Event> eventsIter = events.iterator();
    InvalidField invalidField = new InvalidField();
    while (eventsIter.hasNext()) {
      try {
        event = eventsIter.next();
        event.setGroup(group);
        eventValidator.isEventValid(event, principal, invalidField);
      } catch (AccessDeniedException | ValidationException e) {
        eventDto = new EventDto(event);
        eventDto.setMessage(e.getMessage());
        invalidEvents.add(eventDto);
        eventsToRemove.add(event);
      }
    }
  }

  /**
   * The method updates a single event.
   *
   * @param event which is to be updated (edited)
   * @param principal - the authenticated user
   * @return eventToUpdate - an updated event
   * @throws AccessDeniedException when a coordinator wants to update an event in other location
   * @throws ValidationException when the information provided for the update is incorrect
   */
  public Event updateSingleEvent(Event event, Principal principal, InvalidField invalidField)
      throws AccessDeniedException, ValidationException {
    Event existedEvent = eventRepository.findOne(event.getId());
    Event eventToUpdate = eventValidator.checkEventFields(event, existedEvent);
    eventValidator.isEventUpdateValid(eventToUpdate, principal, invalidField);
    eventRepository.save(eventToUpdate);
    return eventToUpdate;
  }

  /**
   * The method updates the schedule (in fact, a list of events) for the specified group.
   *
   * @param events the list of events that form the schedule
   * @param principal the authenticated user
   * @return eventResourceList - the list of elements of the EventResource type to display them in
   * hateoas style in json.
   * @throws AccessDeniedException when a coordinator wants to update schedule in other location
   * @throws ValidationException when the information provided for the update is incorrect
   */
  @Override
  public ScheduleResponseWrapper updateSchedule(List<Event> events, Principal principal)
      throws AccessDeniedException, ValidationException {
    List<EventDto> invalidEvents = new ArrayList<>();
    Iterator<Event> eventsIter = events.iterator();
    List<Event> eventsToRemove = new ArrayList<>();
    EventDto eventDto;
    Event event = new Event();
    InvalidField invalidField = new InvalidField();
    while (eventsIter.hasNext()) {
      try {
        event = eventsIter.next();
        Event existedEvent = eventRepository.findOne(event.getId());
        Event eventToUpdate = eventValidator.checkEventFields(event, existedEvent);
        eventValidator.isEventUpdateValid(eventToUpdate, principal, invalidField);
        eventRepository.save(eventToUpdate);
      } catch (AccessDeniedException | ValidationException e) {
        eventDto = new EventDto(event);
        eventDto.setMessage(e.getMessage());
        invalidEvents.add(eventDto);
        eventsToRemove.add(event);
      }
    }
    events.removeAll(eventsToRemove);
    ScheduleResponseWrapper response = new ScheduleResponseWrapper(
        events.stream().map(eventResourceAssembler::toResource).collect(
            Collectors.toList()), invalidEvents);
    return response;
  }

  /**
   * Add or update key date to specified group
   *
   * @param events list of key dates to add or update
   * @return <code>EventResponseWrapper</code> that contains info about successful updating and
   * invalid events.
   */

  public List<EventResource> addKeyDates(List<KeyDateDto> events) {
    List<Event> savedEvents = saveKeyDates(events.stream().map(
        KeyDateDto::toEntity).collect(Collectors.toList()));

    return convertToResource(savedEvents);
  }

  private List<Event> saveKeyDates(List<Event> events) {
    for (Event event : events) {
      Event existedEvent = eventRepository.getEventByEventTypeId(
          event.getEventType().getId(), event.getGroup().getId());
      if (existedEvent != null) {
        event.setId(existedEvent.getId());
      }
    }
    eventRepository.save(events);
    return eventRepository.findAll(
        events.stream().map(Event::getId).collect(Collectors.toList()));
  }

  private List<EventResource> convertToResource(Iterable<Event> events) {
    List<EventResource> eventResources = new ArrayList<>();
    events.forEach(event -> eventResources.add(eventResourceAssembler.toResource(event)));
    return eventResources;
  }

  private void prepareEventsForCopy(List<Event> copyWeekEvents, LocalDate start) {
    long diff = ChronoUnit.DAYS.between(DateUtil.getMondayDateOfWeek(start),
        DateUtil.getMondayDateOfWeek(copyWeekEvents.get(0).getStart().toLocalDate()));
    if (diff > 0) {
      for (Event event : copyWeekEvents) {
        event.setStart(event.getStart().plusDays(diff));
        event.setEnd(event.getEnd().plusDays(diff));
      }
    }
    if (diff < 0) {
      for (Event event : copyWeekEvents) {
        event.setStart(event.getStart().minusDays(diff));
        event.setEnd(event.getEnd().minusDays(diff));
      }
    }
  }

  private void generateEventsForPaste(List<Event> copyWeekEvents, LocalDate start,
      LocalDate finish, List<Event> correct, List<Event> incorrect) {
    prepareEventsForCopy(copyWeekEvents, start);
    LocalDateTime eventStart;
    LocalDateTime eventEnd;
    Event temp;
    for (Event event : copyWeekEvents) {
      for (int i = 0; ; i++) {
        eventStart = event.getStart().plusDays(7 * i);
        eventEnd = event.getEnd().plusDays(7 * i);
        if (eventStart.isBefore(start.atStartOfDay())) {
          continue;
        }
        if (eventStart.isAfter(finish.plusDays(1).atStartOfDay())) {
          break;
        }
        temp = new Event(null, eventStart, eventEnd,
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
      throw new IllegalArgumentException(
          messageByLocaleService.getMessage("illegalArgs.schedule.noCopyEvents")
      );
    }
    LocalDate start;
    LocalDate end;

    if (pasteWeekDate != null) {
      start = getMondayDateOfWeek(pasteWeekDate);
      LocalDate temp = LocalDate.now();
      if (start.isBefore(temp)) {
        start = temp;
      }
      end = getSundayDateOfWeek(pasteWeekDate);
    } else {
      start = LocalDate.now();
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
    return eventRepository.getCrossEvents(event.getStart(), event.getEnd(),
        event.getRoom().getId()) != null;
  }
}