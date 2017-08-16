package com.softserve.teamproject.controller;

import static com.softserve.teamproject.StringContainsOneOf.containsOneOfStrings;
import static com.softserve.teamproject.TestData.getEvent;
import static com.softserve.teamproject.TestData.getKeyDateDto;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.teamproject.dto.EventsFilter;
import com.softserve.teamproject.dto.KeyDateDto;
import com.softserve.teamproject.dto.KeyDateWrapper;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.repository.EventRepository;
import com.softserve.teamproject.service.TestGroup;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ScheduleControllerIntegrationTest {

  private final String TEACHER_WITH_GROUPS = "OlegShvets";
  private final String COORDINATOR = "DmytroPetin";
  private final String STUDENT = "Myst1c";
  private final String COORDINATOR_OTHER_LOCATION = "LukasLukichich";
  private final String TEACHER_WITHOUT_GROUPS = "NogroupsUser";
  private final String TEACHER_LOCATION_WITHOUT_GROUPS = "NoOlegShvets";
  @Autowired
  private MockMvc mvc;
  @Autowired
  private EventRepository eventRepository;


  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getEvents_groupIsNotSpecified_allEventsExpected() throws Exception {
    //Arrange
    final int EXPECTED_SIZE = 8;
    final String TESTED_URL = "/events";

    //Act&&Assert
    mvc.perform(get(TESTED_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(EXPECTED_SIZE)));

  }


  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getEvents_groupAndDatesSpecified_eventListExpected() throws Exception {
    //Arrange
    final int EXPECTED_SIZE = 2;
    final int GROUP_ID = 2;
    String start = "2017-07-05";
    String end = "2017-07-24";
    final String TESTED_URL = String
        .format("/events?groupid=%d&start=%s&end=%s", GROUP_ID, start, end);

    //Act&&Assert
    mvc.perform(get(TESTED_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(EXPECTED_SIZE)));

  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getEventsByFilter_groupsAndDatesSpecified_eventListExpected() throws Exception {
    //Arrange
    final int EXPECTED_SIZE = 3;
    List<Integer> GROUP_ID = Arrays.asList(2, 3);
    String start = "2017-07-05";
    String end = "2017-07-24";
    EventsFilter eventsFilter = new EventsFilter();
    eventsFilter.setGroups(GROUP_ID);
    eventsFilter.setStartDate(LocalDate.parse(start));
    eventsFilter.setEndDate(LocalDate.parse(end));
    final String TESTED_URL = "/events/filter";
    List<String> EXPECTED_GROUPS = Arrays.asList("groups/2", "groups/3");

    //Act&&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(eventsFilter)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(EXPECTED_SIZE)))
        .andExpect
            (jsonPath("$[*].links[2].href", everyItem(containsOneOfStrings(EXPECTED_GROUPS))));
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getEventsByFilter_nullGroupsSpecified_badRequestExpected() throws Exception {
    //Arrange
    List<Integer> GROUP_ID = Arrays.asList(null, null);
    String start = "2017-07-05";
    String end = "2017-07-24";
    EventsFilter eventsFilter = new EventsFilter();
    eventsFilter.setGroups(GROUP_ID);
    eventsFilter.setStartDate(LocalDate.parse(start));
    eventsFilter.setEndDate(LocalDate.parse(end));
    final String TESTED_URL = "/events/filter";

    //Act&&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(eventsFilter)))
        .andExpect(status().is(400));

  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getEventsByFilter_emptyGroupListSpecified_emptytListExpected() throws Exception {
    //Arrange
    List<Integer> GROUP_ID = new ArrayList<>();
    String start = "2017-07-05";
    String end = "2017-07-24";
    EventsFilter eventsFilter = new EventsFilter();
    eventsFilter.setGroups(GROUP_ID);
    eventsFilter.setStartDate(LocalDate.parse(start));
    eventsFilter.setEndDate(LocalDate.parse(end));
    final String TESTED_URL = "/events/filter";

    //Act&&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(eventsFilter)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));

  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getEventsByFilter_datesNotSpecified_badRequestExpected() throws Exception {
    //Arrange
    List<Integer> GROUP_ID = new ArrayList<>();
    EventsFilter eventsFilter = new EventsFilter();
    eventsFilter.setGroups(GROUP_ID);
    final String TESTED_URL = "/events/filter";

    //Act&&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(eventsFilter)))
        .andExpect(status().is(400));
  }


  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getKeyEvents_noGroupSpecified_allKeyEventsReturned() throws Exception {
    //Arrange
    final int EXPECTED_SIZE = 6;
    final String TESTED_URL = "/keyevents";
    List<String> keyEventTypesId = Arrays.
        asList("eventTypes/1", "eventTypes/2", "eventTypes/3", "eventTypes/4");

    //Act&Assert
    mvc.perform(get(TESTED_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(EXPECTED_SIZE)))
        .andExpect(
            jsonPath("$[*].links[3].href", everyItem(containsOneOfStrings(keyEventTypesId))));

  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getKeyEvents_groupSpecified_keyEventsReturnedForGroup() throws Exception {
    //Arrange
    final int EXPECTED_SIZE = 4;
    final int GROUP_ID = 2;
    final String TESTED_URL = String.format("/keyevents?groupid=%d", GROUP_ID);
    List<String> keyEventTypesId = Arrays.
        asList("eventTypes/1", "eventTypes/2", "eventTypes/3", "eventTypes/4");

    //Act&Assert
    mvc.perform(get(TESTED_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$[*].links[2].href", everyItem(containsString("groups/2"))))
        .andExpect(
            jsonPath("$[*].links[3].href", everyItem(containsOneOfStrings(keyEventTypesId))));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getKeyEventsByFilter_groupsSpecified_keyEventsReturnedForGroup() throws Exception {
    //Arrange
    final int EXPECTED_SIZE = 6;
    final String TESTED_URL = "/keyevents/filter";
    EventsFilter filter = new EventsFilter();
    filter.setGroups(Arrays.asList(2, 3));
    List<String> keyEventTypesId = Arrays.
        asList("eventTypes/1", "eventTypes/2", "eventTypes/3", "eventTypes/4");
    List<String> groups = Arrays.asList("groups/2", "groups/3");

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(filter)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$[*].links[2].href", everyItem(containsOneOfStrings(groups))))
        .andExpect(
            jsonPath("$[*].links[3].href", everyItem(containsOneOfStrings(keyEventTypesId))));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getKeyEventsByFilter_noGroupsSpecified_nullExpected() throws Exception {
    //Arrange
    final String TESTED_URL = "/keyevents/filter";
    EventsFilter filter = new EventsFilter();
    filter.setGroups(null);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(filter)))
        .andExpect(status().isOk())
        .andExpect(content().string(isEmptyOrNullString()));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getKeyEventsByFilter_emptyRequestBody_badRequestExpected() throws Exception {
    //Arrange
    final String TESTED_URL = "/keyevents/filter";

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(null)))
        .andExpect(status().is(400));
  }


  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getEvent_eventIdSpecified_eventReturned() throws Exception {
    //Arrange
    final int EXPECTED_ID = 1;
    final String TESTED_URL = "/events/1";

    //Act&Assert
    mvc.perform(get(TESTED_URL))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.eventId", is(EXPECTED_ID)));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getEvent_invalidEventID_badRequestExpected() throws Exception {
    //Arrange
    final String TESTED_URL = "/events/abrakadabra";

    //Act&Assert
    mvc.perform(get(TESTED_URL))
        .andExpect(status().is(400));
  }


  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void addKeyDates_validData_keyEventAdded() throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final int EVENT_TYPE = 1;
    LocalDate date = LocalDate.parse("2017-05-16");
    final int EXPECTED_SIZE = 1;
    final String EXPECTED_DATE = "2017-05-16 00:00";
    final String TESTED_URL = "/events/demo";
    List<KeyDateDto> dtoList = new ArrayList<>();
    dtoList.add(getKeyDateDto(GROUP_ID, EVENT_TYPE, date));
    KeyDateWrapper wrapper = new KeyDateWrapper();
    wrapper.setDates(dtoList);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(wrapper)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.succeed", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.succeed[0].dateTime", is(EXPECTED_DATE)))
        .andExpect(jsonPath("$.succeed[0].links[2].href", containsString("groups/" + GROUP_ID)))
        .andExpect(
            jsonPath("$.succeed[0].links[3].href", containsString("eventTypes/" + EVENT_TYPE)));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void addKeyDates_validDataTeacherOfThisGroup_keyEventAdded() throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final int EVENT_TYPE = 1;
    LocalDate date = LocalDate.parse("2017-05-16");
    final int EXPECTED_SIZE = 1;
    final String EXPECTED_DATE = "2017-05-16 00:00";
    final String TESTED_URL = "/events/demo";
    List<KeyDateDto> dtoList = new ArrayList<>();
    dtoList.add(getKeyDateDto(GROUP_ID, EVENT_TYPE, date));
    KeyDateWrapper wrapper = new KeyDateWrapper();
    wrapper.setDates(dtoList);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(wrapper)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.succeed", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.succeed[0].dateTime", is(EXPECTED_DATE)))
        .andExpect(jsonPath("$.succeed[0].links[2].href", containsString("groups/" + GROUP_ID)))
        .andExpect(
            jsonPath("$.succeed[0].links[3].href", containsString("eventTypes/" + EVENT_TYPE)));
  }


  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void addKeyDates_groupIdNotSpecified_invalidEventWithMessageExpected() throws Exception {
    //Arrange
    final int EVENT_TYPE = 1;
    final String EXPECTED_MESSAGE = "Group is not specified";
    LocalDate date = LocalDate.parse("2017-05-16");
    final int EXPECTED_SIZE = 1;
    final String TESTED_URL = "/events/demo";
    List<KeyDateDto> dtoList = new ArrayList<>();
    dtoList.add(getKeyDateDto(null, EVENT_TYPE, date));
    KeyDateWrapper wrapper = new KeyDateWrapper();
    wrapper.setDates(dtoList);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(wrapper)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invalid", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.invalid[0].groupId", isEmptyOrNullString()))
        .andExpect(jsonPath("$.invalid[0].errorMessage", is(EXPECTED_MESSAGE)));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void addKeyDates_eventTypeNotSpecified_invalidEventWithMessageExpected() throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final String EXPECTED_MESSAGE = "Incorrect Event Type";
    LocalDate date = LocalDate.parse("2017-05-16");
    final int EXPECTED_SIZE = 1;
    final String TESTED_URL = "/events/demo";
    List<KeyDateDto> dtoList = new ArrayList<>();
    dtoList.add(getKeyDateDto(GROUP_ID, null, date));
    KeyDateWrapper wrapper = new KeyDateWrapper();
    wrapper.setDates(dtoList);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(wrapper)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invalid", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.invalid[0].eventTypeId", isEmptyOrNullString()))
        .andExpect(jsonPath("$.invalid[0].errorMessage", is(EXPECTED_MESSAGE)));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void addKeyDates_wrongDateSpecified_invalidEventWithMessageExpected() throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final int EVENT_TYPE = 1;
    final String EXPECTED_DATE = "2010-05-16";
    final String EXPECTED_MESSAGE = "Wrong date specified";
    LocalDate date = LocalDate.parse(EXPECTED_DATE);
    final int EXPECTED_SIZE = 1;
    final String TESTED_URL = "/events/demo";
    List<KeyDateDto> dtoList = new ArrayList<>();
    dtoList.add(getKeyDateDto(GROUP_ID, EVENT_TYPE, date));
    KeyDateWrapper wrapper = new KeyDateWrapper();
    wrapper.setDates(dtoList);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(wrapper)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invalid", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.invalid[0].date", is(EXPECTED_DATE)))
        .andExpect(jsonPath("$.invalid[0].errorMessage", is(EXPECTED_MESSAGE)));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void addKeyDates_nullDateSpecified_invalidEventWithMessageExpected() throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final int EVENT_TYPE = 1;
    final String EXPECTED_MESSAGE = "Wrong date specified";
    LocalDate date = null;
    final int EXPECTED_SIZE = 1;
    final String TESTED_URL = "/events/demo";
    List<KeyDateDto> dtoList = new ArrayList<>();
    dtoList.add(getKeyDateDto(GROUP_ID, EVENT_TYPE, date));
    KeyDateWrapper wrapper = new KeyDateWrapper();
    wrapper.setDates(dtoList);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(wrapper)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invalid", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.invalid[0].errorMessage", is(EXPECTED_MESSAGE)));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR_OTHER_LOCATION)
  @Test
  public void addKeyDates_coordinatorInAnotherLocation_invalidEventWithMessageExpected()
      throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final int EVENT_TYPE = 1;
    LocalDate date = LocalDate.parse("2017-05-16");
    final int EXPECTED_SIZE = 1;
    final String EXPECTED_MESSAGE = "Access denied";
    final String TESTED_URL = "/events/demo";
    List<KeyDateDto> dtoList = new ArrayList<>();
    dtoList.add(getKeyDateDto(GROUP_ID, EVENT_TYPE, date));
    KeyDateWrapper wrapper = new KeyDateWrapper();
    wrapper.setDates(dtoList);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(wrapper)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invalid", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.invalid[0].errorMessage", is(EXPECTED_MESSAGE)));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void addKeyDates_teacherOfOtherGroup_invalidEventWithMessageExpected() throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final int EVENT_TYPE = 1;
    LocalDate date = LocalDate.parse("2017-05-16");
    final int EXPECTED_SIZE = 1;
    final String EXPECTED_MESSAGE = "Access denied";
    final String TESTED_URL = "/events/demo";
    List<KeyDateDto> dtoList = new ArrayList<>();
    dtoList.add(getKeyDateDto(GROUP_ID, EVENT_TYPE, date));
    KeyDateWrapper wrapper = new KeyDateWrapper();
    wrapper.setDates(dtoList);

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(wrapper)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.invalid", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.invalid[0].errorMessage", is(EXPECTED_MESSAGE)));
  }


  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void addSchedule_validData_EventCreated() throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final int EVENT_TYPE = 1;
    final int ROOM_ID = 1;
    final int duration = 120;
    LocalDateTime date = LocalDate.parse("2017-08-28").atTime(6, 33);
    final int EXPECTED_SIZE = 1;
    final String TESTED_URL = "/events/groups/" + GROUP_ID;
    List<Event> events = new ArrayList<>();
    events.add(getEvent(GROUP_ID, ROOM_ID, EVENT_TYPE, duration, date));

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(events)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.succeed", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$.succeed[0].dateTime", is("2017-08-28 06:33")))
        .andExpect(jsonPath("$.succeed[0].links[2].href", containsString("groups/" + GROUP_ID)))
        .andExpect(jsonPath("$.succeed[0].links[4].href", containsString("rooms/" + ROOM_ID)));
  }


  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void addSchedule_teacherAuth_forbiddenExpected() throws Exception {
    //Arrange
    final int GROUP_ID = 5;
    final String TESTED_URL = "/events/groups/" + GROUP_ID;
    List<Event> events = new ArrayList<>();

    //Act&Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(events)))
        .andExpect(status().is(403));
  }

}
