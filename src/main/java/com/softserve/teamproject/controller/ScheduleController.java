package com.softserve.teamproject.controller;

import com.softserve.teamproject.dto.EventsFilter;
import com.softserve.teamproject.entity.resource.EventResource;
import com.softserve.teamproject.service.ScheduleService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle events.
 */
@RestController
public class ScheduleController {

  private ScheduleService scheduleService;

  @Autowired
  public void setScheduleService(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  /**
   * Get events by groupId and date interval [start,end]
   *
   * @param groupId is received as a request param
   * @param start date is received as a request param
   * @param end date is received as a request param
   * @return events info by groupId and datetime interval [start,end] or just all events
   */
  @RequestMapping(value = "/events", method = RequestMethod.GET)
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
   * Get events by array of group ids and date interval [start,end]
   *
   * @param requestFilter dto object in json format, need for filter.
   * @return events info by array of group ids and datetime interval [start,end]
   */
  @RequestMapping(value = "/events/filter", method = RequestMethod.POST)
  public Iterable<EventResource> getEventsByFilter(@RequestBody EventsFilter requestFilter) {
    if (requestFilter.getGroups() != null) {
      return scheduleService
          .getEventsByFilter(requestFilter.getGroups(), requestFilter.getStartDate(),
              requestFilter.getEndDate());
    }
    return null;
  }

  /**
   * Get key events by groupId
   *
   * @param groupId is received as a request param
   * @return key events info by groupId or just all key events
   */
  @RequestMapping(value = "/keyevents", method = RequestMethod.GET)
  public Iterable<EventResource> getKeyEvents(
      @RequestParam(value = "groupid", required = false) Integer groupId) {
    if (groupId != null) {
      return scheduleService.getKeyEventsByGroupId(groupId);
    }
    return scheduleService.getAllKeyEvents();
  }

  /**
   * Get key events by array of group ids
   *
   * @param requestFilter dto object in json format, need for filter.
   * @return key events info by array of group ids
   */
  @RequestMapping(value = "/keyevents/filter", method = RequestMethod.POST)
  public Iterable<EventResource> getKeyEventsByFilter(@RequestBody EventsFilter requestFilter) {
    if (requestFilter.getGroups() != null) {
      return scheduleService.getKeyEventsByFilter(requestFilter.getGroups());
    }
    return null;
  }

  /**
   * Get event by id
   *
   * @param id is received as a path variable
   * @return event info
   */
  @RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
  public EventResource getEvent(@PathVariable Integer id) {
    return scheduleService.getEvent(id);
  }

}
