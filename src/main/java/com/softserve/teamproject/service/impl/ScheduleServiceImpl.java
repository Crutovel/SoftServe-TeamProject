package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.repository.expression.EventExpressions.getKeyDates;

import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.entity.assembler.EventResourceAssembler;
import com.softserve.teamproject.entity.resource.EventResource;
import com.softserve.teamproject.repository.EventRepository;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.repository.custom.EventRepositoryCustom;
import com.softserve.teamproject.service.ScheduleService;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private EventRepository eventRepository;
  private GroupRepository groupRepository;
  private UserRepository userRepository;

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
  public void setEventRepository(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  private EventResourceAssembler eventResourceAssembler;

  @Autowired
  public void setEventResourceAssembler(
      EventResourceAssembler eventResourceAssembler) {
    this.eventResourceAssembler = eventResourceAssembler;
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

  private void addSingleEvent(Event event, Principal principal) throws AccessDeniedException {
    User user = userRepository.getUserByNickName(principal.getName());
    if (!event.getGroup().getLocation().equals(user.getLocation())) {
      throw new AccessDeniedException(
          ": The coordinators can create the schedule only in their location");
    } else {
      eventRepository.save(event);
    }
  }

  @Override
  public void addSchedule(List<Event> events, Integer groupId, Principal principal) throws AccessDeniedException {
    Group group = groupRepository.findOne(groupId);
    for (Event event : events) {
      event.setGroup(group);
      addSingleEvent(event, principal);
    }
  }

  private void updateSingleEvent(Event event, Principal principal) throws AccessDeniedException {
    User user = userRepository.getUserByNickName(principal.getName());
    if (!event.getGroup().getLocation().equals(user.getLocation())) {
      throw new AccessDeniedException(
          ": The coordinators can edit the schedule only in their location");
    } else {
      Event eventToUpdate = eventRepository.findOne(event.getId());
      if(!(event.getDateTime()==null)){
        eventToUpdate.setDateTime(event.getDateTime());
      }
      if(!(event.getDuration()==0)){
        eventToUpdate.setDuration(event.getDuration());
      }
      if(!(event.getEventType()==null)){
        eventToUpdate.setEventType(event.getEventType());
      }
      if(!(event.getRoom()==null)){
        eventToUpdate.setRoom(event.getRoom());
      }
      eventRepository.save(eventToUpdate);
    }
  }

  @Override
  public void updateSchedule(List<Event> events, Integer groupId, Principal principal) throws AccessDeniedException {
    Group group = groupRepository.findOne(groupId);
    for (Event event : events) {
      event.setGroup(group);
      updateSingleEvent(event, principal);
    }
  }

  private Iterable<EventResource> convertToResource(Iterable<Event> events) {
    List<EventResource> eventResources = new ArrayList<>();
    events.forEach(event -> eventResources.add(eventResourceAssembler.toResource(event)));
    return eventResources;
  }
}
