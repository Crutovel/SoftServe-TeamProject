package com.softserve.teamproject.entity.assembler;

import com.softserve.teamproject.controller.GroupController;
import com.softserve.teamproject.entity.BudgetOwner;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Location;
import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.entity.resource.GroupResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Use for mapping Group object to GroupResource one and add links to this object.
 */
@Component
public class GroupResourceAssembler extends ResourceAssemblerSupport<Group, GroupResource> {

  private RepositoryEntityLinks repositoryEntityLinks;

  public GroupResourceAssembler() {
    super(GroupController.class, GroupResource.class);
  }

  @Autowired
  public void setRepositoryEntityLinks(
      RepositoryEntityLinks repositoryEntityLinks) {
    this.repositoryEntityLinks = repositoryEntityLinks;
  }

  @Override
  public GroupResource toResource(Group group) {
    GroupResource groupResource = instantiateResource(group);
    initResourceFields(groupResource, group);
    addSelfLinkToResource(groupResource, group);
    addEntityFieldLinkToResource(groupResource, group.getLocation().getId(), Location.class);
    addEntityFieldLinkToResource(groupResource, group.getBudgetOwner().getId(), BudgetOwner.class);
    addCollectionFieldLinkToResource(groupResource, group, "teachers");
    addEntityFieldLinkToResource(groupResource, group.getStatus().getId(), Status.class);
    addEntityFieldLinkToResource(groupResource, group.getStatus().getId(), Specialization.class);
    return groupResource;
  }

  private void addCollectionFieldLinkToResource(GroupResource groupResource, Group group,
      String relationName) {
    Link collectionLink = repositoryEntityLinks.linkForSingleResource(Group.class, group.getId())
        .slash(relationName).withRel(relationName);
    groupResource.add(collectionLink);
  }

  private void addEntityFieldLinkToResource(GroupResource groupResource, int fieldId, Class clazz) {
    Link entityLink = repositoryEntityLinks
        .linkToSingleResource(clazz, fieldId);
    groupResource.add(entityLink);
  }

  private void addSelfLinkToResource(GroupResource groupResource, Group group) {
    Link groupLink = repositoryEntityLinks.linkToSingleResource(Group.class, group.getId());
    Link selfLink = new Link(groupLink.getHref(), Link.REL_SELF);
    groupResource.add(selfLink);
    groupResource.add(groupLink);
  }

  private void initResourceFields(GroupResource groupResource, Group group) {
    groupResource.setGroupId(group.getId());
    groupResource.setName(group.getName());
    groupResource.setStartDate(group.getStartDate());
    groupResource.setFinishDate(group.getFinishDate());
    groupResource.setExperts(group.getExperts());
  }
}
