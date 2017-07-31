package com.softserve.teamproject.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.softserve.teamproject.TestData;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.service.TeacherGroupsManipulationService;
import com.softserve.teamproject.service.impl.GroupServiceImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
public class GroupControllerTest {

  @MockBean
  private GroupServiceImpl groupService;
  @MockBean
  private TeacherGroupsManipulationService teacherGroupsManipulationService;

  @Autowired
  private MockMvc mockMvc;

  private Set<GroupResource> groupResources;

  @WithMockUser
  @Test
  public void getGroupResourcesFromUserLocation_RegisteredUserWithGroups_ReturnGroups()
      throws Exception {
    groupResources = TestData.getGroupResourceSet("DP-115", "DP-116");
    given(groupService.getGroupResourcesFromUserLocation(anyString())).willReturn(groupResources);
    mockMvc.perform(get("/groups/mylocation")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("DP-115"))
        .andExpect(jsonPath("$[1].name").value("DP-116"));
  }

  @WithMockUser
  @Test
  public void getGroupResourcesFromUserLocation_RegisteredUserNoGroups_ReturnEmpty()
      throws Exception {
    mockMvc.perform(get("/groups/mylocation")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("[]"));
  }

  @Test
  public void getGroupResourcesFromUserLocation_NotRegisteredUser_Return401Status()
      throws Exception {
    mockMvc.perform(get("/groups/mylocation")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(401));
  }
}
