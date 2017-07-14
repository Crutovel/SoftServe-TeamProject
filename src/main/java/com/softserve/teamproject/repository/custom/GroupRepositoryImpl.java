package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.Group;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class GroupRepositoryImpl implements
    GroupRepositoryCustom {

  public GroupRepositoryImpl() {
  }

  @PersistenceContext
  private EntityManager entityManager;

  public List<Group> getAllGroupsOfTheTeacher(String teachersName) {
    List<Group> allGroups = new ArrayList<>();
    Iterable<Group> itGroups = entityManager
        .createQuery("from Group group where group.teacher.nickName = :teachersName", Group.class)
        .setParameter("teachersName", teachersName).getResultList();
    itGroups.forEach(allGroups::add);
    return allGroups;
  }
}
