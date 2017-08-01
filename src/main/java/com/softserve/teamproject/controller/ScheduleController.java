package com.softserve.teamproject.controller;

import com.softserve.teamproject.dto.EventsFilter;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.resource.EventResource;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.ScheduleService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import javax.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle events.
 */
@RestController
public class ScheduleController {

  private ScheduleService scheduleService;
  private GroupService groupService;
  private ScheduleService getScheduleService;

  @Autowired
  public void setScheduleService(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  @Autowired
  public void setGroupService(GroupService groupService) {
    this.groupService = groupService;
  }

  @Autowired
  public void setGetScheduleService(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  /**
   * Get events by groupId and date interval [start,end].
   *
   * @param groupId is received as a request param
   * @param start date is received as a request param
   * @param end date is received as a request param
   * @return events info by groupId and datetime interval [start,end] or just all events
   */
  @GetMapping(value = "/events")
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
   * Get events by array of group ids and date interval [start,end].
   *
   * @param requestFilter dto object in json format, need for filter.
   * @return events info by array of group ids and datetime interval [start,end]
   */
  @PostMapping(value = "/events/filter")
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
  @GetMapping(value = "/keyevents")
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
  @PostMapping(value = "/keyevents/filter")
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
  @GetMapping(value = "/events/{id}")
  public EventResource getEvent(@PathVariable Integer id) {
    return scheduleService.getEvent(id);
  }

  /**
   * Method allows to create a schedule (add all events from the list) for the group with the
   * specified id.
   *
   * @param events list of events in JSON format
   * @param id id of the selected group as a url parameter
   */
  @RequestMapping(value = "/events/groups/{id}", method = RequestMethod.POST)
  public List<EventResource> addSchedule(@RequestBody List<Event> events, @PathVariable Integer id,
      Principal principal) throws ValidationException {
    return scheduleService.addSchedule(events, id, principal);
  }

  /**
   * Method allows to edit a schedule (edit all events from the list) for the group with the
   * specified id.
   *
   * @param events list of events in JSON format
   * @param id id of the selected group as a url parameter
   */
  @RequestMapping(value = "/events/groups/{id}", method = RequestMethod.PUT)
  public List<EventResource> editSchedule(@RequestBody List<Event> events, @PathVariable Integer id,
      Principal principal) throws ValidationException {
    return scheduleService.updateSchedule(events, id, principal);
  }


}
