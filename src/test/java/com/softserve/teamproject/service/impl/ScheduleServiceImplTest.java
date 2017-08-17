package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.utils.DateUtil.getWorkWeekOfDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.softserve.teamproject.entity.resource.EventResource;
import com.softserve.teamproject.repository.EventRepository;
import com.softserve.teamproject.service.ScheduleService;
import com.softserve.teamproject.service.TestSchedule;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ScheduleServiceImplTest {

  private final static int PLANNED_GROUP_ID = 1;
  private final static int FINISHED_GROUP_ID = 2;
  private final static int CURRENT_GROUP_ID = 3;
  private final static String TEACHER = "OlegShvets";

  @Autowired
  private EventRepository eventRepository;
  @Autowired
  private ScheduleService scheduleService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @TestSchedule
  @WithUserDetails(TEACHER)
  @Test
  public void getEventsByGroupId_bothDatesNotSpecifiedStatusFinished_lastWeekEventsExpected() {
    //Arrange
    final int EXPECTED_SIZE = 2;
    List<LocalDate> week = getWorkWeekOfDate(LocalDate.parse("2017-08-18"));

    //Act
    List<EventResource> events = scheduleService.getEventsByGroupId(FINISHED_GROUP_ID, null, null);

    //Assert
    assertEquals(EXPECTED_SIZE, events.size());
    events.forEach(event -> assertTrue(week.contains(event.getDateTime().toLocalDate())));
  }

  @TestSchedule
  @WithUserDetails(TEACHER)
  @Test
  public void getEventsByGroupId_bothDatesNotSpecifiedStatusCurrent_currentWeekEventsExpected() {
    //Arrange
    List<LocalDate> week = getWorkWeekOfDate(LocalDate.now());

    //Act
    List<EventResource> events = scheduleService.getEventsByGroupId(CURRENT_GROUP_ID, null, null);

    //Assert
    events.forEach(event -> assertTrue(week.contains(event.getDateTime().toLocalDate())));
  }

  @TestSchedule
  @WithUserDetails(TEACHER)
  @Test(expected = IllegalArgumentException.class)
  public void getEventsByGroupId_bothDatesNotSpecifiedStatusPlanned_exceptionThrown() {
    //Arrange
    //Act
    List<EventResource> events = scheduleService.getEventsByGroupId(PLANNED_GROUP_ID, null, null);

  }

  @TestSchedule
  @WithUserDetails(TEACHER)
  @Test(expected = IllegalArgumentException.class)
  public void getEventsByGroupId_oneDateIsNotSpecifiedCorrectStatus_exceptionExpected() {
    //Arrange
    //Act
    List<EventResource> events = scheduleService
        .getEventsByGroupId(PLANNED_GROUP_ID, LocalDate.now(), null);
  }

  @TestSchedule
  @WithUserDetails(TEACHER)
  @Test
  public void getEventsByGroupId_BothDatesSpecifiedCorrectStatus_eventsReturned() {
    //Arrange
    final int EXPECTED_SIZE = 4;

    //Act
    List<EventResource> events = scheduleService.getEventsByGroupId(
        FINISHED_GROUP_ID, LocalDate.parse("2017-01-01"), LocalDate.parse("2018-01-01"));

    //Assert
    assertEquals(EXPECTED_SIZE, events.size());
  }
}
