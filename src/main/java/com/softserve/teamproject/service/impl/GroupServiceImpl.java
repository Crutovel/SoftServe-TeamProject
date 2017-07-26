package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.dto.GroupsFilter;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.assembler.GroupResourceAssembler;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.LocationRepository;
import com.softserve.teamproject.repository.StatusRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.repository.expression.GroupExpressions;
import com.softserve.teamproject.service.GroupService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  private GroupRepository groupRep;
  private UserRepository userRepository;
  private StatusRepository statusRepository;
  private LocationRepository locationRepository;
  private GroupResourceAssembler groupResourceAssembler;

  @Autowired
  public void setGroupResourceAssembler(
      GroupResourceAssembler groupResourceAssembler) {
    this.groupResourceAssembler = groupResourceAssembler;
  }

  @Autowired
  public void setGroupRepository(GroupRepository groupRep) {
    this.groupRep = groupRep;
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setStatusRepository(StatusRepository statusRepository) {
    this.statusRepository = statusRepository;
  }

  @Autowired
  public void setLocationRepository(
      LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  public List<Group> getAllGroups() {
    return groupRep.findAll();
  }

  @Override
  public List<GroupResource> getAllGroupResources() {
    List<Group> groups = groupRep.findAll();
    List<GroupResource> groupResources = new ArrayList<>();
    groups.forEach(group -> groupResources.add(groupResourceAssembler.toResource(group)));
    return groupResources;
  }

  /**
   * Creates new group based on request body parameters. If a current authorized user is
   * coordinator, the group location must equal coordinator location. If a current authorized user
   * is administrator, the group location can be anyone. For other roles creating of group is
   * unavailable.
   *
   * @param group new group from controller
   * @param userName current authorized user
   * @throws AccessDeniedException if current authorized user has't access to add group
   */
  @Override
  public void addGroup(Group group, String userName) throws AccessDeniedException {
    User user = userRepository.getUserByNickName(userName);

    if (user.getRole().getName().equals("coordinator")
        && !user.getLocation().equals(group.getLocation())) {
      throw new AccessDeniedException("Coordinator can't add group in alien location");
    }

    Status status = statusRepository.getStatusByName("planned");
    group.setStatus(status);

    groupRep.save(group);
  }

  /**
   * Deletes group with given id. If a current authorized user is coordinator, the group location
   * must equal coordinator location. If a current authorized user is administrator, the group
   * location can be anyone. For other roles deleting of group is unavailable.
   *
   * @param groupId given group id
   * @param userName current authorized user
   */
  @Override
  public void deleteGroup(int groupId, String userName) {
    User user = userRepository.getUserByNickName(userName);
    Group group = groupRep.getOne(groupId);

    if (user.getRole().getName().equals("coordinator")
        && !user.getLocation().equals(group.getLocation())) {
      throw new AccessDeniedException("Coordinator can't delete group in alien location");
    }

    groupRep.delete(group);
  }

  /**
   * Returns groups with filter.
   *
   * @param filter contains filter values
   * @return groups with filter
   */
  public Iterable<GroupResource> getGroupsByFilter(GroupsFilter filter) {

    if (filter.getLocations() != null) {
      Iterable<Group> groups = groupRep
          .findAll(GroupExpressions.getByLocationIds(filter.getLocations()));
      List<GroupResource> groupResources = new ArrayList<>();
      groups.forEach(group -> groupResources.add(groupResourceAssembler.toResource(group)));
      return groupResources;
    }

    return getAllGroupResources();
  }

  @Override
  public Set<GroupResource> getGroupResourcesFromUserLocation(String principalName) {
    User currentUser = userRepository.getUserByNickName(principalName);
    Location userLocation = locationRepository.findOne(currentUser.getLocation().getId());
    Set<Group> groups = userLocation.getGroups();
    Set<GroupResource> groupResources = new HashSet<>();
    groups.forEach(group -> groupResources.add(groupResourceAssembler.toResource(group)));
    return groupResources;

  }
}
