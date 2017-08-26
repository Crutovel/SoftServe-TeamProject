package com.softserve.teamproject.controller;

import com.softserve.teamproject.dto.CopyPasteScheduleWrapper;
import com.softserve.teamproject.dto.EventResponseWrapper;
import com.softserve.teamproject.dto.EventsFilter;
import com.softserve.teamproject.dto.KeyDateDto;
import com.softserve.teamproject.dto.KeyDateResponseDto;
import com.softserve.teamproject.dto.ScheduleResponseWrapper;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.resource.EventResource;
import com.softserve.teamproject.service.ScheduleService;
import com.softserve.teamproject.validation.ValidCopyPasteSchedule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle events.
 */
@Validated
@RestController
@Api(value = "scheduleController", description = "Operations with events")
public class ScheduleController {

  private ScheduleService scheduleService;
  private Validator validator;

  @Autowired
  public void setScheduleService(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @Autowired
  public void setValidator(Validator validator) {
    this.validator = validator;
  }

  /**
   * Get events by groupId and date interval [start,end].
   *
   * @param groupId is received as a request param
   * @param start date is received as a request param
   * @param end date is received as a request param
   * @return events info by groupId and datetime interval [start,end] or just all events
   */
  @GetMapping(value = "/events", produces = "application/json")
  @ApiOperation(value = "Get events for given group and dates interval", response = Event.class,
      responseContainer = "List")
  public Iterable<EventResource> getEvents(
      @RequestParam(value = "groupid", required = false) Integer groupId,
      @RequestParam(value = "start", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam(value = "end", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
    if (groupId != null) {
      return scheduleService.getEventsByGroupId(groupId, start, end);
    }
    return scheduleService.getAllEvents();
  }

  /**
   * Get events by groupId and last week.
   *
   * @param groupId is received as a request param
   * @return events info by groupId and last week
   */
  @GetMapping(value = "/events/lastweek", produces = "application/json")
  @ApiOperation(value = "Get events for given group and last week", response = Event.class,
      responseContainer = "List")
  public Iterable<EventResource> getEventsForLastWeek(
      @RequestParam(value = "groupid") Integer groupId) {
    return scheduleService.getLastWeekEvents(groupId);
  }

  /**
   * Get events by array of group ids and date interval [start,end].
   *
   * @param requestFilter dto object in json format, need for filter.
   * @return events info by array of group ids and datetime interval [start,end]
   */
  @PostMapping(value = "/events/filter", produces = "application/json")
  @ApiOperation(value = "Get events for given array of group and dates interval",
      response = Event.class, responseContainer = "List")
  public Iterable<EventResource> getEventsByFilter(@RequestBody EventsFilter requestFilter) {
    if (requestFilter.getGroups() != null) {
      return scheduleService
          .getEventsByFilter(requestFilter.getGroups(), requestFilter.getStartDate(),
              requestFilter.getEndDate());
    }
    return null;
  }

  /**
   * Get key events by groupId.
   *
   * @param groupId is received as a request param
   * @return key events info by groupId or just all key events
   */
  @GetMapping(value = "/events/demo", produces = "application/json")
  @ApiOperation(value = "Get key events for given group", response = Event.class,
      responseContainer = "List")
  public Iterable<EventResource> getKeyEvents(
      @RequestParam(value = "groupid", required = false) Integer groupId) {
    if (groupId != null) {
      return scheduleService.getKeyEventsByGroupId(groupId);
    }
    return scheduleService.getAllKeyEvents();
  }

  /**
   * Get key events by array of group ids.
   *
   * @param requestFilter dto object in json format, need for filter.
   * @return key events info by array of group ids
   */
  @PostMapping(value = "/events/demo/filter", produces = "application/json")
  @ApiOperation(value = "Get key events for given array of group", response = Event.class,
      responseContainer = "List")
  public Iterable<EventResource> getKeyEventsByFilter(@RequestBody EventsFilter requestFilter) {
    if (requestFilter.getGroups() != null) {
      return scheduleService.getKeyEventsByFilter(requestFilter.getGroups());
    }
    return null;
  }

  /**
   * Get event by id.
   *
   * @param id is received as a path variable
   * @return event info
   */
  @GetMapping(value = "/events/{id}", produces = "application/json")
  @ApiOperation(value = "Get event by id", response = Event.class)
  public EventResource getEvent(@PathVariable Integer id) {
    return scheduleService.getEvent(id);
  }

  private String getAllConstraintViolations(
      Set<ConstraintViolation<KeyDateDto>> constraintViolations) {
    StringBuilder message = new StringBuilder();
    for (ConstraintViolation<KeyDateDto> violation : constraintViolations) {
      message.append(violation.getMessage());
    }
    return message.toString();
  }

  private void validateKeyEvents(List<KeyDateDto> src, List<KeyDateDto> validEvents,
      List<KeyDateResponseDto> invalidEvents) {
    Set<ConstraintViolation<KeyDateDto>> constraintViolations;
    for (KeyDateDto date : src) {
      constraintViolations = validator.validate(date);
      if (constraintViolations.size() > 0) {
        invalidEvents.add(date.toResponseDto(getAllConstraintViolations(constraintViolations)));
      } else {
        validEvents.add(date);
      }
    }
  }

  @PostMapping(value = "/events/demo")
  @ApiOperation(value = "Add key events for selected groups", response = EventResponseWrapper.class)
  public EventResponseWrapper addKeyDates(@ApiParam("Only need date, event type id, group id")
  @RequestBody List<KeyDateDto> dates) {
    List<KeyDateDto> validEvents = new ArrayList<>();
    List<KeyDateResponseDto> invalidEvents = new ArrayList<>();
    validateKeyEvents(dates, validEvents, invalidEvents);
    return new EventResponseWrapper(scheduleService.addKeyDates(validEvents), invalidEvents);
  }

  /**
   * Method allows to create a schedule (add all events from the list) for the group with the
   * specified id.
   *
   * @param groupId is received as a request param
   * @param events list of events in JSON format
   */
  @PostMapping(value = "/events")
  @ApiOperation(value = "Add schedule (list of events) for the selected group", response = Event.class,
      responseContainer = "List")
  public ScheduleResponseWrapper addSchedule(@RequestBody List<Event> events,
      @RequestParam(value = "groupid") Integer groupId, Principal principal)
      throws ValidationException {
    return scheduleService.addSchedule(events, groupId, principal);
  }

  /**
   * Method allows to edit a schedule (edit all events from the list).
   *
   * @param events list of events in JSON format
   */
  @PutMapping(value = "/events")
  @ApiOperation(value = "Edit schedule (list of events)", response = Event.class,
      responseContainer = "List")
  public ScheduleResponseWrapper editSchedule(@RequestBody List<Event> events, Principal principal)
      throws ValidationException {
    return scheduleService.updateSchedule(events, principal);
  }

  /**
   * Method allows to copy paste a schedule for the group with the specified id.
   *
   * @param copyPasteSchedule wrapper for request info such as group id, copy date, paste date
   * @return copyPasteSchedule wrapper with group id, copy date, paste date and conflicts
   */

  @PostMapping("/events/copypaste")
  public CopyPasteScheduleWrapper copyPasteSchedule(
      @RequestBody @ValidCopyPasteSchedule @Valid CopyPasteScheduleWrapper copyPasteSchedule) {
    copyPasteSchedule.setConflicts(scheduleService.copyPasteSchedule(copyPasteSchedule));
    return copyPasteSchedule;
  }

}
