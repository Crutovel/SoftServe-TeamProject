package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.repository.expression.GroupExpressions.getByLocationIds;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.LocationRepository;
import com.softserve.teamproject.repository.SpecializationRepository;
import com.softserve.teamproject.repository.StatusRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.GroupService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  private GroupRepository groupRep;
  private UserRepository userRepository;
  private StatusRepository statusRepository;
  private LocationRepository locationRepository;
  private SpecializationRepository specializationRepository;

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

  public List<Group> getGroupsByLocationIds(Integer[] locationIds) {
    return groupRep.getGroupsByLocationIds(locationIds);
  }

  public List<Group> getAllGroups() {
    return groupRep.findAll();
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

    Set<User> teachers = group.getTeachers()
        .stream()
        .map(User::getId)
        .map(id -> userRepository.getOne(id))
        .collect(Collectors.toSet());

    Status status = statusRepository.getStatusByName("planned");

    Location location = locationRepository.getOne(group.getLocation().getId());

    Specialization specialization = specializationRepository
        .getOne(group.getSpecialization().getId());

    group.setTeachers(teachers);
    group.setStatus(status);
    group.setLocation(location);
    group.setSpecialization(specialization);

    groupRep.save(group);
  }

  /**
   * Deletes group with given id. If a current authorized user is
   * coordinator, the group location must equal coordinator location. If a current authorized user
   * is administrator, the group location can be anyone. For other roles deleting of group is
   * unavailable.
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
}
