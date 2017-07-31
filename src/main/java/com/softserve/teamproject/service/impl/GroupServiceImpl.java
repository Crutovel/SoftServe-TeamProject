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
import java.lang.reflect.Field;
import java.util.List;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
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
    if (!isValid(group)) {
      throw new ValidationException("This group already exists");
    }

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
   * Updates group.
   * If the user's role - teacher, the group must have this teacher who must be
   * at the same location as group, and group status must be < graduated.
   * If the user's role - coordinator, he must be at the same location as group.
   * If the user's role - administrator,  the group location can be anyone.
   * For other roles updating groups is unavailable.
   *
   * @param group is a current group
   * @param currentStatus is a status of group which will be updated
   * @param userName is a nickname of current authorized user
   * @throws AccessDeniedException if current authorized user has't access to update group
   */
  @Override
  public void updateGroup(Group group, Status currentStatus, String userName)
      throws AccessDeniedException {
//    fieldsCheck(group);
    User user = userRepository.getUserByNickName(userName);

    if (user.getRole().getName().equals("teacher")) {
      if (group.getTeachers().contains(user) && !user.getLocation().equals(group.getLocation())) {
        throw new AccessDeniedException("Teacher can't edit group in alien location");
      } else if (!group.getTeachers().contains(user)) {
        throw new AccessDeniedException("Teacher can't edit group which doesn't assigned to him.");
      } else if (group.getTeachers().contains(user)
          && user.getLocation().equals(group.getLocation()) && currentStatus
          .getName().equalsIgnoreCase("graduated")) {
        throw new AccessDeniedException("Teacher can't edit group which is graduated.");
      }
    } else if (user.getRole().getName().equals("coordinator")
        && !user.getLocation().equals(group.getLocation())) {
      throw new AccessDeniedException("Coordinator can't edit group in alien location");
    }

    groupRep.save(group);
  }

  public void fieldsCheck(Group group) {
    Group existedGroup = getGroupById(group.getId());
    Class<?> groupClass = group.getClass();
    for (Field field : groupClass.getDeclaredFields()) {
      field.setAccessible(true);
      try {
        if (field.get(group) == null) {
          Field existedField = existedGroup.getClass().getDeclaredField(field.getName());
          existedField.setAccessible(true);
            field.set(group, existedField.get(existedGroup));
          }
      } catch (IllegalAccessException | NoSuchFieldException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Gets group by id.
   *
   * @param id is id of a group
   * @return group with the current id
   */
  @Override
  public Group getGroupById(Integer id) {
    return groupRep.findOne(id);
  }

  @Override
  public boolean isValid(Group group) {
    Group existed = groupRep.findByName(group.getName());
    return existed == null;
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
