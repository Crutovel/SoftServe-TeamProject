package com.softserve.teamproject;

import com.softserve.teamproject.entity.BudgetOwner;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.entity.resource.GroupResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    group.setExperts(new LinkedHashSet<String>(){{
      add("Sergey");
    }});
    return group;
  }
}
