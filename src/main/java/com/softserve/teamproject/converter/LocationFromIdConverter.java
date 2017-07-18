package com.softserve.teamproject.converter;

import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Retrieve Location from database by it Id when Spring parse JSON field which contains location id
 */
public class LocationFromIdConverter  implements Converter<String, Location> {

  private LocationRepository locationRepository;

  @Autowired
  public void setLocationRepository(
    LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  @Override
  public Location convert(String source) {
    return locationRepository.findOne(Integer.valueOf(source));
  }
}
