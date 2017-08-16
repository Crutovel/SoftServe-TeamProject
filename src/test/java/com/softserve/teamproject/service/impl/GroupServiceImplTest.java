package com.softserve.teamproject.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;

import com.softserve.teamproject.TestData;
import com.softserve.teamproject.dto.GroupsFilter;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.entity.assembler.GroupResourceAssembler;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.LocationRepository;
import com.softserve.teamproject.repository.StatusRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.TestGroup;
import com.softserve.teamproject.validation.GroupValidator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
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
  @Autowired
  private LocationRepository locationRepository;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private StatusRepository statusRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private GroupValidator groupValidator;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    doNothing().when(groupResourceAssembler).initLinks(any(), any());
    groupService.setGroupResourceAssembler(groupResourceAssembler);
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void getGroupById_notExistGroupId_ReturnNull() {
    //Arrange
    final int GROUP_ID = 28;
    //Act
    Group group = groupService.getGroupById(GROUP_ID);
    //Assert
    assertNull(group);
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void getGroupResourceById_notExistGroupId_returnNull() {
    //Arrange
    final int GROUP_ID = 28;

    //Act
    GroupResource group = groupService.getGroupResourceById(GROUP_ID);

    //Assert
    assertNull(group);
  }

  @TestGroup
  @Test
  public void fieldsCheck_cleanName_fillNameFieldInGroupObject() {
    //Arrange
    final String EXPECTED_NAME = "DP-115";
    Group group = groupRepository.findOne(1);
    entityManager.detach(group);
    group.setName(null);

    //Act
    groupValidator.fieldsCheck(group);

    //Assert
    assertEquals(EXPECTED_NAME, group.getName());
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void getGroupById_existGroupId_returnGroup() {
    //Arrange
    final int GROUP_ID = 1;
    final String EXPECTED_GROUP_NAME = "DP-115";

    //Act
    Group group = groupService.getGroupById(GROUP_ID);

    //Assert
    assertEquals(EXPECTED_GROUP_NAME, group.getName());
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void getGroupResourceById_existGroupId_returnGroup() {
    //Arrange
    final int GROUP_ID = 1;
    final String EXPECTED_GROUP_NAME = "DP-115";

    //Act
    GroupResource group = groupService.getGroupResourceById(GROUP_ID);

    //Assert
    assertEquals(EXPECTED_GROUP_NAME, group.getName());
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void getAllGroupResources_teacher_returnAllGroupResources() {
    //Arrange
    final int EXPECTED_GROUP_COUNT = 3;

    //Act
    List<GroupResource> list = groupService.getAllGroupResources();

    //Assert
    assertEquals(EXPECTED_GROUP_COUNT, list.size());
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void deleteGroup_coordinatorHisLocationPlannedGroup_deleteGroup() {
    //Arrange
    final int DELETED_GROUP_ID = 1;

    //Act
    groupService.deleteGroup(DELETED_GROUP_ID, COORDINATOR);
    Group deleted = groupRepository.findOne(DELETED_GROUP_ID);

    //Assert
    assertNull(deleted);
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void deleteGroup_coordinatorHisLocationInProcessGroup_deleteGroup() {
    //Arrange
    final int DELETED_GROUP_ID = 2;
    final String DELETED_GROUP_NAME = "DP-116";

    //Act
    groupService.deleteGroup(DELETED_GROUP_ID, COORDINATOR);
    Group deleted = groupRepository.findOne(DELETED_GROUP_ID);

    //Assert
    assertEquals(DELETED_GROUP_NAME, deleted.getName());
    assertTrue(deleted.isDeleted());
  }

  @WithUserDetails(COORDINATOR_OTHER_LOCATION)
  @TestGroup
  @Test(expected = AccessDeniedException.class)
  public void deleteGroup_coordinatorOtherLocation_exceptionThrown() {
    //Act
    groupService.deleteGroup(1, COORDINATOR_OTHER_LOCATION);
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getGroupsByFilter_twoLocationsExist_returnGroups() {
    //Arrange
    final int EXPECTED_SIZE = 4;
    Integer[] groupLocations = new Integer[]{1, 2};
    GroupsFilter filter = new GroupsFilter() {{
      setLocations(groupLocations);
    }};

    //Act
    Iterable<GroupResource> groups = groupService.getGroupsByFilter(filter);
    final int RESULT_SIZE = (int) groups.spliterator().getExactSizeIfKnown();

    //Assert
    assertEquals(EXPECTED_SIZE, RESULT_SIZE);
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getGroupsByFilter_oneLocationsExists_returnGroups() {
    //Arrange
    final int EXPECTED_SIZE = 3;
    Integer[] groupLocations = new Integer[]{1};
    GroupsFilter filter = new GroupsFilter() {{
      setLocations(groupLocations);
    }};

    //Act
    Iterable<GroupResource> groups = groupService.getGroupsByFilter(filter);
    final int RESULT_SIZE = (int) groups.spliterator().getExactSizeIfKnown();

    //Assert
    assertEquals(EXPECTED_SIZE, RESULT_SIZE);
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void getGroupsByFilter_threeLocationOneNotExist_returnGroups() {
    //Arrange
    final int EXPECTED_SIZE = 4;
    Integer[] groupLocations = new Integer[]{1, 2, 5};
    GroupsFilter filter = new GroupsFilter() {{
      setLocations(groupLocations);
    }};

    //Act
    Iterable<GroupResource> groups = groupService.getGroupsByFilter(filter);
    final int RESULT_SIZE = (int) groups.spliterator().getExactSizeIfKnown();

    //Assert
    assertEquals(EXPECTED_SIZE, RESULT_SIZE);
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void addGroup_coordinatorNewName_groupAdd() {
    //Arrange
    final String NEW_GROUP_NAME = "DP-NEW";
    final String NEW_GROUP_STATUS = "planned";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);
    Location location = locationRepository.findOne(newGroup.getLocation().getId());
    newGroup.setLocation(location);

    //Act
    GroupResource addedGroupResource = groupService.addGroup(newGroup, COORDINATOR);
    Group addedGroup = groupRepository.findByName(NEW_GROUP_NAME);

    //Assert
    assertEquals(NEW_GROUP_NAME, addedGroup.getName());
    assertEquals(NEW_GROUP_NAME, addedGroupResource.getName());
    assertEquals(NEW_GROUP_STATUS, addedGroup.getStatus().getName());
  }

  @WithUserDetails(COORDINATOR_OTHER_LOCATION)
  @TestGroup
  @Test(expected = AccessDeniedException.class)
  public void addGroup_coordinatorOtherLocation_exceptionThrown() {
    //Arrange
    final String NEW_GROUP_NAME = "DP-NEW";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);
    Location location = locationRepository.findOne(newGroup.getLocation().getId());
    newGroup.setLocation(location);

    //Act
    groupService.addGroup(newGroup, COORDINATOR_OTHER_LOCATION);
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test(expected = ValidationException.class)
  public void addGroup_nameAlreadyExists_exceptionThrown() {
    //Arrange
    final String NEW_GROUP_NAME = "DP-115";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);

    //Act
    groupService.addGroup(newGroup, COORDINATOR);
  }

  @WithUserDetails(COORDINATOR)
  @TestGroup
  @Test
  public void updateGroup_coordinatorGroupGraduated_editGroup() {
    //Arrange
    final int SET_SIZE = 2;
    Group edited = groupRepository.findByName("DP-116");
    edited.setExperts(new LinkedHashSet<String>() {{
      add("Sergey");
      add("Anton");
    }});
    entityManager.detach(edited);

    //Act
    GroupResource updatedResource = groupService
        .updateGroup(edited, edited.getStatus(), COORDINATOR);
    Group updated = groupRepository.findByName("DP-116");

    //Assert
    assertTrue(updated.getExperts().containsAll(edited.getExperts()));
    assertEquals(SET_SIZE, updated.getExperts().size());
    assertEquals(edited.getName(), updatedResource.getName());
  }

  @WithUserDetails(TEACHER_WITH_GROUPS)
  @TestGroup
  @Test(expected = AccessDeniedException.class)
  public void updateGroup_teacherGroupGraduated_exceptionThrown() {
    //Arrange
    Group edited = groupRepository.findByName("DP-116");
    entityManager.detach(edited);

    //Act
    groupService.updateGroup(edited, edited.getStatus(), COORDINATOR_OTHER_LOCATION);
  }

  @WithUserDetails(COORDINATOR_OTHER_LOCATION)
  @TestGroup
  @Test(expected = AccessDeniedException.class)
  public void updateGroup_coordinatorOtherLocation_exceptionThrown() {
    //Arrange
    Group edited = groupRepository.findByTeachers_NickName(TEACHER_WITH_GROUPS).get(0);
    entityManager.detach(edited);

    //Act
    groupService.updateGroup(edited, edited.getStatus(), COORDINATOR_OTHER_LOCATION);
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void updateGroup_coordinatorWithoutGroup_editGroup() {
    //Arrange
    Group edited = groupRepository.findByTeachers_NickName(TEACHER_WITH_GROUPS).get(0);
    entityManager.detach(edited);

    //Act
    edited.setName("NEW-EDITED-GROUP");
    edited.setExperts(new HashSet<>());
    GroupResource updatedResource = groupService
        .updateGroup(edited, edited.getStatus(), COORDINATOR);
    Group updated = groupRepository.findOne(edited.getId());

    //Assert
    assertEquals(edited.getName(), updated.getName());
    assertEquals(edited.getExperts(), updated.getExperts());
    assertEquals(edited.getName(), updatedResource.getName());
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test(expected = ValidationException.class)
  public void updateGroup_coordinatorWithoutGroupEditNameExistValue_exceptionThrown() {
    //Arrange
    Group edited = groupRepository.findByTeachers_NickName(TEACHER_WITH_GROUPS).get(0);
    entityManager.detach(edited);

    //Act
    edited.setName("DP-116");
    groupService.updateGroup(edited, edited.getStatus(), COORDINATOR);
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test(expected = AccessDeniedException.class)
  public void updateGroup_teacherWithoutGroupEditName_exceptionThrown() {
    //Arrange
    Group edited = groupRepository.findAll().get(0);

    //Act
    groupService.updateGroup(edited, edited.getStatus(), TEACHER_WITHOUT_GROUPS);
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void updateGroup_teacherWithGroupEditNewNameAndCleanExperts_editGroup() {
    //Arrange
    Group edited = TestData.getGroup("EDITED-GROUP");
    edited.setId(1);
    edited.setTeachers(new LinkedHashSet<User>() {{
      add(userRepository.getUserByNickName(TEACHER_WITH_GROUPS));
    }});
    edited.setStatus(statusRepository.getStatusByName("in-process"));
    edited.setLocation(locationRepository.findOne(1));

    //Act
    GroupResource updatedResource = groupService
        .updateGroup(edited, edited.getStatus(), TEACHER_WITH_GROUPS);
    Group updated = groupRepository.findOne(edited.getId());

    //Assert
    assertEquals(edited.getName(), updated.getName());
    assertEquals(edited.getExperts(), updated.getExperts());
    assertEquals(edited.getName(), updatedResource.getName());
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test(expected = ValidationException.class)
  public void updateGroup_teacherWithGroupEditNewNameNotValidValue_exceptionThrown() {
    //Arrange
    Group edited = groupRepository.findByTeachers_NickName(TEACHER_WITH_GROUPS).get(0);

    //Act
    edited.setName("EDITED_GROUP");
    groupService.updateGroup(edited, edited.getStatus(), TEACHER_WITH_GROUPS);
  }

  @TestGroup
  @WithUserDetails(TEACHER_LOCATION_WITHOUT_GROUPS)
  @Test
  public void getGroupResourcesFromUserLocation_teacherLocationWithoutGroups_returnEmpty() {
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
  public void getGroupResourcesFromUserLocation_teacherLocationWithGroups_returnGroups() {
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
