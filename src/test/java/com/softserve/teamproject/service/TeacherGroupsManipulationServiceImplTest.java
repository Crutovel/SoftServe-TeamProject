package com.softserve.teamproject.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;

import com.softserve.teamproject.entity.assembler.GroupResourceAssembler;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.service.impl.TeacherGroupsManipulationServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TeacherGroupsManipulationServiceImplTest {

  private final String COORDINATOR = "DmytroPetin";
  private final String TEACHER_WITH_GROUPS = "OlegShvets";
  @Spy
  private GroupResourceAssembler groupResourceAssembler;
  @Autowired
  private TeacherGroupsManipulationServiceImpl teacherService;
  @Autowired
  private GroupRepository groupRepository;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    doNothing().when(groupResourceAssembler).initLinks(any(), any());
    teacherService.setGroupResourceAssembler(groupResourceAssembler);
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getAllGroupResourcesOfTheTeacher_correctTeacherName_ReturnGroupResources() {
    //Arrange
    final List<String> EXPECTED_NAMES = Arrays.asList("DP-115", "DP-116");
    final int EXPECTED_LIST_SIZE = 2;
    //Act
    List<GroupResource> list = teacherService.getAllGroupResourcesOfTheTeacher(TEACHER_WITH_GROUPS);
    List<String> result_names = list.stream().map(GroupResource::getName)
        .collect(Collectors.toList());
    //Assert
    assertEquals(EXPECTED_LIST_SIZE, list.size());
    assertTrue(EXPECTED_NAMES.containsAll(result_names));
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getAllGroupResourcesOfTheTeacher_incorrectTeacherName_ReturnZero() {
    //Arrange
    final String INCORRECT_NAME = "Vasya";
    final int EXPECTED_LIST_SIZE = 0;
    //Act
    List<GroupResource> list = teacherService.getAllGroupResourcesOfTheTeacher(INCORRECT_NAME);
    //Assert
    assertEquals(EXPECTED_LIST_SIZE, list.size());
  }
}
