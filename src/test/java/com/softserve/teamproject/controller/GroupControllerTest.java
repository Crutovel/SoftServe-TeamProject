package com.softserve.teamproject.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import com.softserve.teamproject.TestData;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Example of unit test for GroupController
 */
@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class GroupControllerTest {

  @Autowired
  protected WebApplicationContext wac;
  @Autowired
  protected FilterChainProxy springSecurityFilterChain;
  @MockBean
  private GroupService groupService;
  @MockBean
  private TeacherGroupsManipulationService teacherGroupsManipulationService;
  @Autowired
  private MockMvc mockMvc;
  private Set<GroupResource> groupResources;

  @WithMockUser
  @Test
  public void getGroupsFromUserLocation_registeredUserLocationWithGroups_returnGroups()
      throws Exception {
    //Arrange
    final String FIRST_GROUP = "DP-115";
    final String SECOND_GROUP = "DP-116";
    final int RESULT_SET_SIZE = 2;
    final String TESTED_URL = "/groups/mylocation";
    final int EXPECTED_NUMBER_OF_INVOCATION = 1;
    groupResources = TestData.getGroupResourceSet(FIRST_GROUP, SECOND_GROUP);
    given(groupService.getGroupResourcesFromUserLocation(anyString()))
        .willReturn(groupResources);

    //Act && Assert
    mockMvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)))
        .andExpect(jsonPath("$[0].name").value(FIRST_GROUP))
        .andExpect(jsonPath("$[1].name").value(SECOND_GROUP));

    //Assert
    verify(groupService, times(EXPECTED_NUMBER_OF_INVOCATION))
        .getGroupResourcesFromUserLocation(anyString());
    verifyNoMoreInteractions(groupService);
  }

  @WithMockUser
  @Test
  public void getGroupsFromUserLocation_registeredUserLocationWithoutGroups_returnEmpty()
      throws Exception {
    //Arrange
    final int RESULT_SET_SIZE = 0;
    final String TESTED_URL = "/groups/mylocation";
    final int EXPECTED_NUMBER_OF_INVOCATION = 1;
    groupResources = new HashSet<>();
    given(groupService.getGroupResourcesFromUserLocation(anyString()))
        .willReturn(groupResources);

    //Act && Assert
    mockMvc.perform(get(TESTED_URL).with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)));

    //Assert
    verify(groupService, times(EXPECTED_NUMBER_OF_INVOCATION))
        .getGroupResourcesFromUserLocation(anyString());
    verifyNoMoreInteractions(groupService);
  }

  @Test
  public void getGroupsFromUserLocation_notRegisteredUser_return401Status()
      throws Exception {
    //Arrange
    final String TESTED_URL = "/groups/mylocation";
    final int EXPECTED_HTTP_STATUS = 401;
    final int EXPECTED_NUMBER_OF_INVOCATION = 0;

    //Act && Assert
    mockMvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(EXPECTED_HTTP_STATUS));

    verify(groupService, times(EXPECTED_NUMBER_OF_INVOCATION))
        .getGroupResourcesFromUserLocation(anyString());
  }
}
