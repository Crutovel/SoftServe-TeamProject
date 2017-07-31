package com.softserve.teamproject.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.booleanThat;
import static org.mockito.Mockito.doNothing;

import com.softserve.teamproject.dto.GroupsFilter;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.assembler.GroupResourceAssembler;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.service.impl.GroupServiceImpl;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import javax.validation.constraints.AssertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GroupServiceImplTest {

  private final String TEACHER_LOCATION_WITH_GROUPS = "OlegShvets";
  private final String TEACHER_WITH_GROUPS = "OlegShvets";
  private final String COORDINATOR = "DmytroPetin";
  private final String COORDINATOR_OTHER_LOCATION = "LukasLukichich";
  private final String TEACHER_WITHOUT_GROUPS = "NogroupsUser";
  private final String TEACHER_LOCATION_WITHOUT_GROUPS = "NoOlegShvets";
  @Spy
  private GroupResourceAssembler groupResourceAssembler;
  @Autowired
  private GroupServiceImpl groupService;
  @Autowired
  private GroupRepository groupRepository;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    doNothing().when(groupResourceAssembler).initLinks(any(), any());
    groupService.setGroupResourceAssembler(groupResourceAssembler);
  }

  @Test
  public void isValid_Stub() {
//    @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
//    List<Group> getAllGroups();
//
//    @PreAuthorize("hasAnyAuthority('teacher','coordinator', 'admin')")
//    List<GroupResource> getAllGroupResources();
//
//    @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
//    void addGroup(Group group, String userName) throws AccessDeniedException;
//
//    @PreAuthorize("hasAnyAuthority('coordinator', 'admin')")
//    void deleteGroup(int groupId, String userName) throws AccessDeniedException;
//
//    @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
//    void updateGroup(Group group, Status currentStatus, String userName) throws AccessDeniedException;
//
//    @PreAuthorize("hasAnyAuthority('teacher', 'coordinator', 'admin')")
//    Group getGroupById(Integer id);
//    Iterable<GroupResource> getGroupsByFilter(GroupsFilter filter);
    fail();
  }

  @WithUserDetails(COORDINATOR_OTHER_LOCATION)
  @TestGroup
  @Test(expected = AccessDeniedException.class)
  public void updateGroup_CoordinatorOtherLocation_ExceptionThrown() {
    //Arrange
    Group edited = groupRepository.findByTeachers_NickName(TEACHER_WITH_GROUPS).get(0);

    //Act
    groupService.updateGroup(edited, edited.getStatus(), COORDINATOR_OTHER_LOCATION);
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void updateGroup_CoordinatorWithoutGroup_EditGroup() {
    //Arrange
    Group edited = groupRepository.findByTeachers_NickName(TEACHER_WITH_GROUPS).get(0);

    //Act
    edited.setName("NEW_EDITED_GROUP");
    edited.setExperts(new HashSet<>());
    groupService.updateGroup(edited, edited.getStatus(), COORDINATOR);
    Group updated = groupRepository.findOne(edited.getId());

    //Assert
    assertEquals(edited.getName(),updated.getName());
    assertEquals(edited.getExperts(),updated.getExperts());
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test(expected = AccessDeniedException.class)
  public void updateGroup_TeacherWithoutGroupEditName_ExceptionThrown() {
    //Arrange
    Group edited = groupRepository.findAll().get(0);

    //Act
    groupService.updateGroup(edited, edited.getStatus(), TEACHER_WITHOUT_GROUPS);
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void updateGroup_TeacherWithGroupEditNameAndExperts_EditGroup() {
    //Arrange
    Group edited = groupRepository.findByTeachers_NickName(TEACHER_WITH_GROUPS).get(0);

    //Act
    edited.setName("EDITED_GROUP");
    edited.setExperts(null);
    groupService.updateGroup(edited, edited.getStatus(), TEACHER_WITH_GROUPS);
    Group updated = groupRepository.findOne(edited.getId());

    //Assert
    assertEquals(edited.getName(),updated.getName());
    assertEquals(edited.getExperts(),updated.getExperts());
  }

  @TestGroup
  @WithUserDetails(TEACHER_LOCATION_WITHOUT_GROUPS)
  @Test
  public void getGroupResourcesFromUserLocation_TeacherLocationWithoutGroups_ReturnEmpty() {
    //Arrange
    final int EXPECTED_SIZE = 0;

    //Act
    Set<GroupResource> groups = groupService
        .getGroupResourcesFromUserLocation(TEACHER_LOCATION_WITHOUT_GROUPS);

    //Assert
    assertEquals(EXPECTED_SIZE, groups.size());
  }

  @TestGroup
  @WithUserDetails(TEACHER_LOCATION_WITH_GROUPS)
  @Test
  public void getGroupResourcesFromUserLocation_TeacherLocationWithGroups_ReturnGroups() {
    //Arrange
    final int EXPECTED_SIZE = 2;
    List<String> expected = new ArrayList() {{
      add("DP-115");
      add("DP-116");
    }};

    //Act
    Set<GroupResource> groups = groupService
        .getGroupResourcesFromUserLocation(TEACHER_LOCATION_WITH_GROUPS);
    int result = (int) groups.stream()
        .map(GroupResource::getName)
        .filter(name -> expected.contains(name))
        .count();

    //Assert
    assertEquals(EXPECTED_SIZE, result);
  }
}
