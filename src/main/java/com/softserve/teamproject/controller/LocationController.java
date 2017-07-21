package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.LocationService;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle locations.
 */
@RestController
public class LocationController {

  private LocationService locationService;
  private GroupService groupService;

  @Autowired
  public void setGroupService(GroupService groupService) {
    this.groupService = groupService;
  }

  @Autowired
  public void setLocationService(LocationService locationService) {
    this.locationService = locationService;
  }

  /**
   * Get groups by location ids
   *
   * @param locationIds array of location ids
   * @return groups info
   */
  @RequestMapping(value = "/groups/locations/{locationIds}", method = RequestMethod.GET)
  public List<Group> getGroupsByLocations(@PathVariable("locationIds") String[] locationIds) {

    Integer[] array = Arrays.stream(locationIds).mapToInt(Integer::parseInt).boxed()
        .toArray(Integer[]::new);

    List<Group> result = groupService.getGroupsByLocationIds(array);
    if (result.size() == 0) {
      throw new EntityNotFoundException();
    }
    return result;
  }

  /**
   * Get locations info
   *
   * @return locations info
   */
  @RequestMapping(value = "/locations", method = RequestMethod.GET)
  public List<Location> getAllLocations() {
    return locationService.getAllLocations();
  }
}
