package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.resource.EventResource;
import java.time.LocalDate;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ScheduleService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource>  getAllEvents();

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource> getAllKeyEvents();

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource>  getEventsByGroupId(Integer groupId,LocalDate start, LocalDate end);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource> getKeyEventsByGroupId(Integer groupId);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource>  getEventsByFilter(Integer[] groupId, LocalDate start, LocalDate end);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  Iterable<EventResource>  getKeyEventsByFilter(Integer[] groupId);

  @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
  EventResource getEvent(Integer id);
}
