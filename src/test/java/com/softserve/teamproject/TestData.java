package com.softserve.teamproject;

import com.softserve.teamproject.dto.KeyDateDto;
import com.softserve.teamproject.entity.BudgetOwner;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.entity.resource.GroupResource;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
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

  public static List<LocalDate> getWeek(LocalDate date) {
    TemporalField temporalField = WeekFields.of(Locale.forLanguageTag("ru")).dayOfWeek();
    LocalDate start = date.with(temporalField, 1);
    return Stream.iterate(start, d -> d.plusDays(1)).limit(5).collect(Collectors.toList());
  }
}
