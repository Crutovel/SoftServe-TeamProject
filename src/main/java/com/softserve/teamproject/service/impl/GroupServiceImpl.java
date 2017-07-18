package com.softserve.teamproject.service.impl;

import static com.softserve.teamproject.repository.expression.GroupExpressions.getByLocationIds;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.User;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.StatusRepository;
import com.softserve.teamproject.repository.UserRepository;
import com.softserve.teamproject.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

  private GroupRepository groupRep;
  private UserRepository userRepository;
  private StatusRepository statusRepository;

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

  public List<Group> getGroupsByLocationIds(Integer[] locationIds) {
    return groupRep.getGroupsByLocationIds(locationIds);
  }

  public List<Group> getAllGroups() {
    return groupRep.findAll();
  }

  @Override
  public boolean addGroup(Group group, String userName) {
    User user = userRepository.getUserByNickName(userName);
    Status status = statusRepository.getStatusByName("planned");
    group.setStatus(status);
    if (user.getRole().getName().equals("coordinator")
        && !user.getLocation().equals(group.getLocation())) {
      return false;
    }
    return (groupRep.save(group) != null);
  }

  @Override
  public boolean deleteGroup(int groupId, String userName) {
    User user = userRepository.getUserByNickName(userName);
    Group group = groupRep.getOne(groupId);
    if (user.getRole().getName().equals("coordinator")
        && !user.getLocation().equals(group.getLocation())) {
      return false;
    }
    groupRep.delete(group);
    return true;
  }
}
