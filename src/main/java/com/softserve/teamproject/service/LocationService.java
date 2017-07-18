package com.softserve.teamproject.service;

import com.softserve.teamproject.entity.Location;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LocationService {

  @PreAuthorize("hasAnyAuthority('teacher','coordinator','admin')")
   List<Location> getAllLocations();
}
