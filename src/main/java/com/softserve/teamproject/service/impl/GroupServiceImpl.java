package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.dto.GroupDto;
import com.softserve.teamproject.dto.GroupsFilter;
import com.softserve.teamproject.dto.StudentDto;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.entity.assembler.GroupResourceAssembler;
import com.softserve.teamproject.entity.resource.GroupResource;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.LocationRepository;
import com.softserve.teamproject.repository.StatusRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.repository.expression.GroupExpressions;
import com.softserve.teamproject.service.GroupService;
import com.softserve.teamproject.service.MessageByLocaleService;
import com.softserve.teamproject.validation.GroupValidator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
  private GroupValidator groupValidator;
  private MessageByLocaleService messageByLocaleService;

  @Value("${group.planned}")
  private String plannedGroupStatus;

  @Autowired
  public void setMessageByLocaleService(
      MessageByLocaleService messageByLocaleService) {
    this.messageByLocaleService = messageByLocaleService;
  }

  @Autowired
  public void setGroupValidator(GroupValidator groupValidator) {
    this.groupValidator = groupValidator;
  }

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

  @Override
  public List<GroupResource> getAllGroupResources() {
    List<Group> groups = groupRep.getUndeletedGroups();
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
  public GroupResource addGroup(Group group, String userName) throws AccessDeniedException {
    if (!groupValidator.isValid(group)) {
      throw new ValidationException(
          messageByLocaleService.getMessage("valid.error.group.exist"));
    }
    User user = userRepository.getUserByNickName(userName);
    groupValidator.checkCoordinatorLocationToManipulateGroup(user, group);
    Status status = statusRepository.getStatusByName(plannedGroupStatus);
    group.setStatus(status);
    group = groupRep.save(group);
    return groupResourceAssembler.toResource(group);
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
    Group group = groupRep.findOne(groupId);
    if (group == null) {
      throw new ValidationException(messageByLocaleService.getMessage("valid.error.group.notExist"));
    }
    groupValidator.checkCoordinatorLocationToManipulateGroup(user, group);
    if (!group.getStatus().getName().equalsIgnoreCase(plannedGroupStatus)) {
      group.setDeleted(true);
      groupRep.save(group);
    } else {
      groupRep.delete(group);
    }
  }

  /**
   * Updates group. If the user's role - teacher, the group must have this teacher who must be at
   * the same location as group, and group status must be < graduated. If the user's role -
   * coordinator, he must be at the same location as group. If the user's role - administrator,  the
   * group location can be anyone. For other roles updating groups is unavailable.
   *
   * @param group is a current group
   * @param currentStatus is a status of group which will be updated
   * @param userName is a nickname of current authorized user
   * @throws AccessDeniedException if current authorized user has't access to update group
   */
  @Override
  public GroupResource updateGroup(Group group, Status currentStatus, String userName)
      throws AccessDeniedException {
    if (!groupValidator.isValidGroupName(group)) {
      throw new ValidationException(messageByLocaleService.getMessage("valid.error.group.exist"));
    }
    User user = userRepository.getUserByNickName(userName);
    groupValidator.checkGroupEditPermissions(user, group, currentStatus);
    group = groupRep.save(group);
    return groupResourceAssembler.toResource(group);
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

  /**
   * Gets group resource by group id.
   *
   * @param id is id of a group
   * @return group resource with the current group id.
   */
  @Override
  public GroupResource getGroupResourceById(Integer id) {
    Group group = groupRep.findOne(id);
    if (group == null) {
      return null;
    }
    return groupResourceAssembler.toResource(group);
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

  @Override
  public List<GroupDto> getAllGroupsDto() {
    List<Group> allGroups = groupRep.findAll();
    return allGroups.stream()
        .map(GroupDto::new)
        .collect(Collectors.toList());
  }
}
