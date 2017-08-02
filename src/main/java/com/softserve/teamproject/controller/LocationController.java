package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
   * Get locations info.
   *
   * @return locations info
   */
  @GetMapping(value = "/locations")
  public List<Location> getAllLocations() {
    return locationService.getAllLocations();
  }
}
