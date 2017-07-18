package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.repository.LocationRepository;
import com.softserve.teamproject.service.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

  private LocationRepository locationRep;

  @Autowired
  public void setLocationRepository(LocationRepository locationRep) {
    this.locationRep = locationRep;
  }

  public List<Location> getAllLocations() {
    return locationRep.findAll();
  }
}
