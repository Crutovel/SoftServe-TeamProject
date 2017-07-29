package com.softserve.teamproject.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;

import com.softserve.teamproject.entity.assembler.GroupResourceAssembler;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.service.impl.GroupServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GroupServiceImplTest {

  @Autowired
  private GroupServiceImpl groupService;
  @Spy
  private GroupResourceAssembler groupResourceAssembler;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Sql({"/test-clean-table.sql", "/test-data.sql"})
  @WithUserDetails("OlegShvets")
  @Transactional
  @Test
  public void getGroupResourcesFromUserLocation_UserTeacher_ReturnGroups() {
    //Arrange
    final int EXPECTED_SIZE = 2;
    doNothing().when(groupResourceAssembler).initLinks(any(), any());
    groupService.setGroupResourceAssembler(groupResourceAssembler);
    List<String> expected = new ArrayList() {{
      add("DP-115");
      add("DP-116");
    }};

    //Act
    Set<GroupResource> groups = groupService.getGroupResourcesFromUserLocation("OlegShvets");
    int res = (int) groups.stream()
      .map(GroupResource::getName)
      .filter(name -> expected.contains(name))
      .count();

    //Assert
    assertEquals(EXPECTED_SIZE, res);
  }
}
