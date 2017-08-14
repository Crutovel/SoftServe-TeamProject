package com.softserve.teamproject.service;

import com.softserve.teamproject.dto.CopyPasteScheduleWrapper;
import com.softserve.teamproject.dto.EventDto;
import com.softserve.teamproject.dto.ScheduleResponseWrapper;
import com.softserve.teamproject.dto.EventResponseWrapper;
import com.softserve.teamproject.dto.KeyDateDto;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.resource.EventResource;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.ValidationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

public interface ScheduleService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource> getAllEvents();

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource> getAllKeyEvents();

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource> getEventsByGroupId(Integer groupId, LocalDate start, LocalDate end);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource> getKeyEventsByGroupId(Integer groupId);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource> getEventsByFilter(Integer[] groupId, LocalDate start, LocalDate end);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource> getKeyEventsByFilter(Integer[] groupId);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  EventResource getEvent(Integer id);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  EventResponseWrapper addKeyDates(List<KeyDateDto> events, BindingResult result);

  @PreAuthorize("hasAuthority('coordinator')")
  ScheduleResponseWrapper addSchedule(List<Event> events, Integer groupId, Principal principal)
      throws AccessDeniedException, ValidationException;

  @PreAuthorize("hasAuthority('coordinator')")
  ScheduleResponseWrapper updateSchedule(List<Event> events, Principal principal)
      throws AccessDeniedException, ValidationException;

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventDto> copyPasteSchedule(CopyPasteScheduleWrapper copyPasteSchedule);
}
