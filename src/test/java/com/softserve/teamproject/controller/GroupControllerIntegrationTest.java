package com.softserve.teamproject.controller;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.teamproject.TestData;
import com.softserve.teamproject.dto.GroupsFilter;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.service.TestGroup;
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
public class GroupControllerIntegrationTest {

  private final String TEACHER_WITH_GROUPS = "OlegShvets";
  private final String COORDINATOR = "DmytroPetin";
  private final String STUDENT = "Myst1c";
  private final String COORDINATOR_OTHER_LOCATION = "LukasLukichich";
  private final String TEACHER_WITHOUT_GROUPS = "NogroupsUser";
  private final String TEACHER_LOCATION_WITHOUT_GROUPS = "NoOlegShvets";
  @Autowired
  private MockMvc mvc;
  @Autowired
  private GroupRepository groupRepository;

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getGroupsByFilter_twoLocationsExist_returnGroupResources() throws Exception {
    //Arrange
    final int EXPECTED_SIZE = 4;
    Integer[] groupLocations = new Integer[]{1, 2};
    GroupsFilter filter = new GroupsFilter() {{
      setLocations(groupLocations);
    }};
    final String TESTED_URL = "/groups/filter";
    final String[] EXPECTED_GROUP_NAMES = {"DP-115", "DP-116", "DP-1115", "SO-115"};

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(filter)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$[*].name", containsInAnyOrder(EXPECTED_GROUP_NAMES)));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getGroupsByFilter_threeLocationOneNotExist_returnGroupResources() throws Exception {
    //Arrange
    final int EXPECTED_SIZE = 4;
    Integer[] groupLocations = new Integer[]{1, 2, 5};
    GroupsFilter filter = new GroupsFilter() {{
      setLocations(groupLocations);
    }};
    final String TESTED_URL = "/groups/filter";
    final String[] EXPECTED_GROUP_NAMES = {"DP-115", "DP-116", "DP-1115", "SO-115"};

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(filter)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(EXPECTED_SIZE)))
        .andExpect(jsonPath("$[*].name", containsInAnyOrder(EXPECTED_GROUP_NAMES)));
  }

  @TestGroup
  @Test
  public void getGroupsByFilter_notRegisteredUser_unauthorizedStatus() throws Exception {
    //Arrange
    Integer[] groupLocations = new Integer[]{1, 2, 5};
    GroupsFilter filter = new GroupsFilter() {{
      setLocations(groupLocations);
    }};
    final String TESTED_URL = "/groups/filter";
    final int EXPECTED_HTTP_STATUS = 401;

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(filter)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(STUDENT)
  @Test
  public void getGroupsByFilter_notAllowedUser_forbiddenStatus() throws Exception {
    //Arrange
    Integer[] groupLocations = new Integer[]{1, 2, 5};
    GroupsFilter filter = new GroupsFilter() {{
      setLocations(groupLocations);
    }};
    final String TESTED_URL = "/groups/filter";
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(filter)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getGroupById_allowedUserAndExistGroupId_returnGroupResource() throws Exception {
    //Arrange
    final String EXPECTED_GROUP_NAME = "DP-115";
    final Integer GROUP_ID = 1;
    final String TESTED_URL = "/groups/{id}";

    //Act && Assert
    mvc.perform(get(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(EXPECTED_GROUP_NAME)))
        .andExpect(jsonPath("$.groupId", is(GROUP_ID)));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getGroupById_allowedUserAndNotExistGroupId_returnEmptyResult() throws Exception {
    //Arrange
    final Integer GROUP_ID = 1000;
    final String TESTED_URL = "/groups/{id}";

    //Act && Assert
    mvc.perform(get(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(is(isEmptyOrNullString())))
    ;
  }

  @TestGroup
  @Test
  public void getGroupById_notRegisteredUser_unauthorizedStatus() throws Exception {
    //Arrange
    final Integer GROUP_ID = 1;
    final String TESTED_URL = "/groups/{id}";
    final int EXPECTED_HTTP_STATUS = 401;

    //Act && Assert
    mvc.perform(get(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(STUDENT)
  @Test
  public void getGroupById_notAllowedUser_forbiddenStatus() throws Exception {
    //Arrange
    final Integer GROUP_ID = 1;
    final String TESTED_URL = "/groups/{id}";
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(get(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void editGroup_coordinatorAndGroupFromHisLocation_updateGroup() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final Integer NEW_GROUP_ID = 125;
    final String NEW_GROUP_NAME = "DP-115-NEW";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);
    editedGroup.setId(NEW_GROUP_ID);

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(NEW_GROUP_NAME)))
        .andExpect(jsonPath("$.groupId", is(GROUP_ID)));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void editGroup_teacherAndHisNotGraduatedGroup_updateGroup() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final String NEW_GROUP_NAME = "NEW-GROUP";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(NEW_GROUP_NAME)))
        .andExpect(jsonPath("$.groupId", is(GROUP_ID)));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void editGroup_teacherAndNotValidGroup_badRequestStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final String NOT_VALID_GROUP_NAME = "NEW_GROUP";
    Group editedGroup = TestData.getGroup(NOT_VALID_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 400;

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR_OTHER_LOCATION)
  @Test
  public void editGroup_coordinatorAndGroupFromOtherLocation_forbiddenStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final String NEW_GROUP_NAME = "NEW-GROUP";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void editGroup_existName_badRequestStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final String NEW_GROUP_NAME = "DP-116";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 400;

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(TEACHER_LOCATION_WITHOUT_GROUPS)
  @Test
  public void editGroup_teacherAndGroupFromOtherLocation_forbiddenStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final String NEW_GROUP_NAME = "EDIT-NAME";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void editGroup_teacherAndNotHisGroup_forbiddenStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final String NEW_GROUP_NAME = "DP-115-OTHER";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void editGroup_teacherAndGraduatedGroup_forbiddenStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 2;
    final String NEW_GROUP_NAME = "DP-OTHER-NAME";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @Test
  public void editGroup_notRegisteredUser_unauthorizedStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final String NEW_GROUP_NAME = "DP-115-EDITED";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 401;

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(STUDENT)
  @Test
  public void editGroup_notAllowedUser_forbiddenStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final String NEW_GROUP_NAME = "DP-115-EDITED";
    Group editedGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(editedGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void deleteGroup_coordinatorAndNotPlannedGroup_markGroupAsDeleted() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 2;

    //Act && Assert
    mvc.perform(delete(TESTED_URL, GROUP_ID))
        .andExpect(status().isOk());

    //Assert
    Group deletedGroup = groupRepository.findOne(GROUP_ID);
    assertTrue(deletedGroup.isDeleted());
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void deleteGroup_coordinatorAndPlannedGroup_deleteGroup() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;

    //Act && Assert
    mvc.perform(delete(TESTED_URL, GROUP_ID))
        .andExpect(status().isOk());

    //Assert
    Group deletedGroup = groupRepository.findOne(GROUP_ID);
    assertNull(deletedGroup);
  }

  @TestGroup
  @WithUserDetails(COORDINATOR_OTHER_LOCATION)
  @Test
  public void deleteGroup_coordinatorFromOtherLocation_forbiddenStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(delete(TESTED_URL, GROUP_ID))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void deleteGroup_notExistGroupId_badRequestStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 100;
    final int EXPECTED_HTTP_STATUS = 400;

    //Act && Assert
    mvc.perform(delete(TESTED_URL, GROUP_ID))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void deleteGroup_notAllowedUser_forbiddenStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(delete(TESTED_URL, GROUP_ID))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @Test
  public void deleteGroup_notRegisteredUser_unauthorizedStatus() throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/{id}";
    final Integer GROUP_ID = 1;
    final int EXPECTED_HTTP_STATUS = 401;

    //Act && Assert
    mvc.perform(delete(TESTED_URL, GROUP_ID))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void createGroup_coordinatorAndValidGroup_returnGroupResource() throws Exception {
    //Arrange
    final String NEW_GROUP_NAME = "NEW-GROUP";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);
    final String TESTED_URL = "/groups";

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newGroup)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.groupId", any(Integer.class)))
        .andExpect(jsonPath("$.name", is(NEW_GROUP_NAME)));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void createGroup_coordinatorAndInvalidGroup_badRequestStatus() throws Exception {
    //Arrange
    final String NEW_GROUP_NAME = "NEW-GROUP";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);
    newGroup.setLocation(null);
    final String TESTED_URL = "/groups";
    final int EXPECTED_HTTP_STATUS = 400;

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void createGroup_coordinatorAndGroupWithExistName_badRequestStatus() throws Exception {
    //Arrange
    final String NEW_GROUP_NAME = "DP-115";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);
    newGroup.setLocation(null);
    final String TESTED_URL = "/groups";
    final int EXPECTED_HTTP_STATUS = 400;

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR_OTHER_LOCATION)
  @Test
  public void createGroup_coordinatorFromOtherLocation_forbiddenStatus() throws Exception {
    //Arrange
    final String NEW_GROUP_NAME = "DP-NEW";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);
    final String TESTED_URL = "/groups";
    final int EXPECTED_HTTP_STATUS = 403;

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @Test
  public void createGroup_notRegisteredUser_unauthorizedStatus() throws Exception {
    //Arrange
    final String NEW_GROUP_NAME = "DP-NEW";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 401;
    final String TESTED_URL = "/groups";

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void createGroup_notAllowedUser_forbiddenStatus() throws Exception {
    //Arrange
    final String NEW_GROUP_NAME = "DP-NEW";
    Group newGroup = TestData.getGroup(NEW_GROUP_NAME);
    final int EXPECTED_HTTP_STATUS = 403;
    final String TESTED_URL = "/groups";

    //Act && Assert
    mvc.perform(post(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newGroup)))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void getTeachersGroups_teacherWithGroups_returnGroups() throws Exception {
    //Arrange
    final String[] EXPECTED_GROUP_NAMES = {"DP-115", "DP-116"};
    final int RESULT_SET_SIZE = 2;
    final String TESTED_URL = "/groups/my";

    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)))
        .andExpect(jsonPath("$[*].name", containsInAnyOrder(EXPECTED_GROUP_NAMES)));
  }

  @TestGroup
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void getTeachersGroups_teacherWithoutGroups_returnEmptyResult()
      throws Exception {
    //Arrange
    final int RESULT_SET_SIZE = 0;
    final String TESTED_URL = "/groups/my";

    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)));
  }

  @TestGroup
  @Test
  public void getTeachersGroups_notRegisteredUser_unauthorizedStatus() throws Exception {
    //Arrange
    final int EXPECTED_HTTP_STATUS = 401;
    final String TESTED_URL = "/groups/my";

    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(STUDENT)
  @Test
  public void getTeachersGroups_notAllowedUser_forbiddenStatus() throws Exception {
    //Arrange
    final int EXPECTED_HTTP_STATUS = 403;
    final String TESTED_URL = "/groups/my";

    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(COORDINATOR)
  @Test
  public void getGroupsFromUserLocation_allowedUserAndLocationWithGroups_returnGroups()
      throws Exception {
    //Arrange
    final String[] EXPECTED_GROUP_NAMES = {"DP-115", "DP-116", "DP-1115"};
    final int RESULT_SET_SIZE = 3;
    final String TESTED_URL = "/groups/mylocation";

    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)))
        .andExpect(jsonPath("$[*].name", containsInAnyOrder(EXPECTED_GROUP_NAMES)));
  }

  @TestGroup
  @WithUserDetails(TEACHER_LOCATION_WITHOUT_GROUPS)
  @Test
  public void getGroupsFromUserLocation_allowedUserAndLocationWithoutGroups_returnEmptyResult()
      throws Exception {
    //Arrange
    final int RESULT_SET_SIZE = 0;
    final String TESTED_URL = "/groups/mylocation";

    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)));
  }

  @TestGroup
  @Test
  public void getGroupsFromUserLocation_notRegisteredUser_unauthorizedStatus() throws Exception {
    //Arrange
    final int EXPECTED_HTTP_STATUS = 401;
    final String TESTED_URL = "/groups/mylocation";

    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }

  @TestGroup
  @WithUserDetails(STUDENT)
  @Test
  public void getGroupsFromUserLocation_notAllowedUser_forbiddenStatus()
      throws Exception {
    //Arrange
    final int EXPECTED_HTTP_STATUS = 403;
    final String TESTED_URL = "/groups/mylocation";

    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));
  }
}
