package com.softserve.teamproject.controller;

import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.service.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that used for handle locations.
 */
@RestController
public class LocationController {

  private LocationService locationService;

  @Autowired
  public void setLocationService(LocationService locationService) {
    this.locationService = locationService;
  }

  /**
   * Get locations info
   *
   * @return locations info
   */
  @RequestMapping(value = "/locations", method = RequestMethod.GET)
  @ResponseBody
  public List<Location> getAllLocations() {
    return locationService.getAllLocations();
  }
}
