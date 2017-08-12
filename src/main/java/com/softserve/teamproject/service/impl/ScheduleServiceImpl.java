package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.repository.expression.EventExpressions.getKeyDates;

import com.softserve.teamproject.dto.EventResponseWrapper;
import com.softserve.teamproject.dto.KeyDateDto;
import com.softserve.teamproject.dto.KeyDateResponseDto;
import com.softserve.teamproject.dto.ScheduleResponseWrapper;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.assembler.EventResourceAssembler;
import com.softserve.teamproject.entity.resource.EventResource;
import com.softserve.teamproject.repository.EventRepository;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.service.ScheduleService;
import com.softserve.teamproject.validation.EventValidator;
import com.softserve.teamproject.validation.impl.InvalidField;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private EventRepository eventRepository;
  private GroupRepository groupRepository;
  private EventValidator eventValidator;
  private EventResourceAssembler eventResourceAssembler;


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

  private List<EventResource> getLastWeekEvents(Integer groupId) {
    Group group = groupRepository.findOne(groupId);
    TemporalField temporalField = WeekFields.of(Locale.forLanguageTag("ru")).dayOfWeek();
    LocalDate start;
    LocalDate end;
    if (group.getStatus().getStatusCategory().getName().equals("current")) {
      start = LocalDate.now().with(temporalField, 1);
      end = LocalDate.now().with(temporalField, 5);
    } else if (group.getStatus().getStatusCategory().getName().equals("finished")) {
      LocalDate finished = group.getFinishDate();
      start = finished.with(temporalField, 1);
      end = finished.with(temporalField, 5);
    } else {
      throw new IllegalArgumentException("Group is in planned state");
    }
    return getEventsByGroupId(groupId, start, end);
  }

  public List<EventResource> getEventsByGroupId(Integer groupId, LocalDate start,
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
      List<Integer> groupId, LocalDate start, LocalDate end) {
    if (start == null || end == null) {
      throw new IllegalArgumentException("Dates must be specified");
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
   * hateoas style in json
   * @throws AccessDeniedException when a coordinator wants to create a schedule in other location
   * @throws ValidationException when the  information is incorrect
   */
  @Override
  public ScheduleResponseWrapper addSchedule(List<Event> events, Integer groupId,
      Principal principal)
      throws AccessDeniedException, ValidationException {
    Group group = groupRepository.findOne(groupId);
    HashMap<String, String> invalidEvents = new HashMap<>();
    List<Event> eventsToRemove = new ArrayList<>();
    processAllEventsBeforeAdd(events, invalidEvents, eventsToRemove, group, principal);
    events.removeAll(eventsToRemove);
    eventRepository.save(events);

    return new ScheduleResponseWrapper(
        events.stream().map(eventResourceAssembler::toResource).collect(Collectors.toList()),
        invalidEvents);
  }

  private void processAllEventsBeforeAdd(List<Event> events, HashMap<String, String> invalidEvents,
      List<Event> eventsToRemove, Group group, Principal principal) {
    Event event = new Event();
    Iterator<Event> eventsIter = events.iterator();
    InvalidField invalidField = new InvalidField();
    while (eventsIter.hasNext()) {
      try {
        event = eventsIter.next();
        event.setGroup(group);
        eventValidator.isEventValid(event, principal, invalidField);
      } catch (AccessDeniedException | ValidationException e) {
        invalidEvents.put(invalidField.getName(), e.getMessage());
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
   * hateoas style in json
   * @throws AccessDeniedException when a coordinator wants to update schedule in other location
   * @throws ValidationException when the information provided for the update is incorrect
   */
  @Override
  public ScheduleResponseWrapper updateSchedule(List<Event> events, Principal principal)
      throws AccessDeniedException, ValidationException {
    HashMap<String, String> invalidEvents = new HashMap<>();
    Iterator<Event> eventsIter = events.iterator();
    List<Event> eventsToRemove = new ArrayList<>();
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
        invalidEvents.put(invalidField.getName(), e.getMessage());
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
   * invalid events
   */

  public EventResponseWrapper addKeyDates(List<KeyDateDto> events, BindingResult result) {
    List<KeyDateResponseDto> invalidEvents = new ArrayList<>();
    result.getFieldErrors().forEach(error -> {
      events.remove(KeyDateDto.class.cast(error.getRejectedValue()));
      invalidEvents
          .add(((KeyDateDto) error.getRejectedValue()).toResponseDto(error.getDefaultMessage()));
    });

    List<Event> savedEvents = saveKeyDates(events.stream().map(
        KeyDateDto::toEntity).collect(Collectors.toList()));

    return new EventResponseWrapper(convertToResource(savedEvents), invalidEvents);
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
}
