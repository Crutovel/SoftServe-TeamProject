package com.softserve.teamproject.entity.assembler;

import com.softserve.teamproject.controller.ScheduleController;
import com.softserve.teamproject.entity.Event;
import com.softserve.teamproject.entity.EventType;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Room;
import com.softserve.teamproject.entity.resource.EventResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Use for mapping Event object to EventResource one and add links to this object.
 */
@Component
public class EventResourceAssembler extends ResourceAssemblerSupport<Event, EventResource> {

  private RepositoryEntityLinks repositoryEntityLinks;

  @Autowired
  public void setRepositoryEntityLinks(
      RepositoryEntityLinks repositoryEntityLinks) {
    this.repositoryEntityLinks = repositoryEntityLinks;
  }

  public EventResourceAssembler() {
    super(ScheduleController.class, EventResource.class);
  }

  @Override
  public EventResource toResource(Event event) {
    EventResource eventResource = instantiateResource(event);
    initResourceFields(eventResource, event);
    addSelfLinkToResource(eventResource, event);

    addEntityFieldLinkToResource(eventResource, event.getGroup().getId(), Group.class);
    addEntityFieldLinkToResource(eventResource, event.getEventType().getId(), EventType.class);
    if (event.getRoom() != null) {
      addEntityFieldLinkToResource(eventResource, event.getRoom().getId(), Room.class);
    }
    return eventResource;
  }

  private void addCollectionFieldLinkToResource(EventResource eventResource, Event event,
      String relationName) {
    Link collectionLink = repositoryEntityLinks.linkForSingleResource(Event.class, event.getId())
        .slash(relationName).withRel(relationName);
    eventResource.add(collectionLink);
  }

  private void addEntityFieldLinkToResource(EventResource eventResource, int fieldId, Class clazz) {
    Link entityLink = repositoryEntityLinks
        .linkToSingleResource(clazz, fieldId);
    eventResource.add(entityLink);
  }

  private void addSelfLinkToResource(EventResource eventResource, Event event) {
    Link eventLink = repositoryEntityLinks.linkToSingleResource(Event.class, event.getId());
    Link selfLink = new Link(eventLink.getHref(), Link.REL_SELF);
    eventResource.add(selfLink);
    eventResource.add(eventLink);
  }

  private void initResourceFields(EventResource eventResource, Event event) {

    eventResource.setEventId(event.getId());
    eventResource.setDateTime(event.getDateTime());
    eventResource.setDuration(event.getDuration());
  }
}
