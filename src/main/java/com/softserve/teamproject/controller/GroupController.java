package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.service.GroupService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle groups.
 */
@RestController
public class GroupController {

  private GroupService groupService;

  @Autowired
  public void setLocationService(GroupService groupService) {
    this.groupService = groupService;
  }

  /**
   * Get groups by location ids
   *
   * @param locationIds array of location ids
   * @return groups info
   */
  @RequestMapping(value = "/groups/{locationIds}", method = RequestMethod.GET)
  @ResponseBody
  public Iterable getGroupsByLocations(@PathVariable("locationIds") String[] locationIds) {

    Integer[] array = Arrays.stream(locationIds).mapToInt(Integer::parseInt).boxed()
        .toArray(Integer[]::new);

    return groupService.getGroupsByLocationIds(array);
  }

  @RequestMapping(value = "/groups", method = RequestMethod.GET)
  @ResponseBody
  public List<Group> getAllGroups() {
    return groupService.getAllGroups();
  }

}
