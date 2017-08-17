package com.softserve.teamproject;

import com.softserve.teamproject.dto.KeyDateDto;
import com.softserve.teamproject.entity.BudgetOwner;
import com.softserve.teamproject.entity.EnglishLevel;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Expert;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.resource.GroupResource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Test data for test classes and methods
 */
public abstract class TestData {

  public static Set<GroupResource> getGroupResourceSet(String... names) {
    Set<GroupResource> groupResources = new LinkedHashSet<>(getGroupResourceList(names));
    return groupResources;
  }

  public static Student getStudent(String firstName) {
    Student student = new Student();
    student.setFirstName(firstName);
    student.setLastName("Carrey");
    student.setEnglishLevel(new EnglishLevel() {{
      setId(4);
    }});
    Expert expert = new Expert();
    expert.setId(1);
    student.setTestApprovedByExpert(expert);
    return student;
  }

  public static List<GroupResource> getGroupResourceList(String... names) {
    List<GroupResource> groupResources = new ArrayList<>();
    for (String name : names) {
      GroupResource groupResource = new GroupResource();
      groupResource.setName(name);
      groupResources.add(groupResource);
    }
    return groupResources;
  }

  public static Group getGroup(String name) {
    Group group = new Group();
    group.setName(name);
    group.setLocation(new Location() {{
      setName("Dnipro");
      setId(1);
    }});
    group.setSpecialization(new Specialization() {{
      setName("JAVA");
      setId(7);
    }});
    group.setBudgetOwner(new BudgetOwner() {{
      setId(1);
      setName("SOFTSERVE");
    }});
    group.setExperts(new LinkedHashSet<String>() {{
      add("Sergey");
    }});
    return group;
  }

  public static KeyDateDto getKeyDateDto(Integer groupId, Integer eventTypeId, LocalDate date) {
    KeyDateDto keyDateDto = new KeyDateDto();
    Group group = new Group();
    group.setId(groupId);
    EventType eventType = new EventType();
    eventType.setId(eventTypeId);
    keyDateDto.setGroup(group);
    keyDateDto.setEventType(eventType);
    keyDateDto.setDate(date);
    return keyDateDto;
  }

  public static Event getEvent(
      Integer groupId, Integer roomId, Integer eventTypeId, int duration, LocalDateTime time) {
    Event event = new Event();
    Group group = new Group();
    group.setId(groupId);
    event.setGroup(group);
    event.setDateTime(time);
    event.setDuration(duration);
    Room room = new Room();
    room.setId(roomId);
    event.setRoom(room);
    EventType eventType = new EventType();
    eventType.setId(eventTypeId);
    event.setEventType(eventType);
    return event;
  }

  public static List<LocalDate> getWeek(LocalDate date) {
    TemporalField temporalField = WeekFields.of(Locale.forLanguageTag("ru")).dayOfWeek();
    LocalDate start = date.with(temporalField, 1);
    return Stream.iterate(start, d -> d.plusDays(1)).limit(5).collect(Collectors.toList());
  }
}
