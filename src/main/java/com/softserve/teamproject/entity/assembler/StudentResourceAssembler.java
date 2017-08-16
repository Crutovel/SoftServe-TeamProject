package com.softserve.teamproject.entity.assembler;

import com.softserve.teamproject.controller.StudentController;
import com.softserve.teamproject.entity.EnglishLevel;
import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.entity.resource.StudentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Use for mapping Student object to StudentResource one and add links to this object.
 */
@Component
public class StudentResourceAssembler extends ResourceAssemblerSupport<Student, StudentResource> {

  private RepositoryEntityLinks repositoryEntityLinks;

  @Autowired
  public void setRepositoryEntityLinks(
      RepositoryEntityLinks repositoryEntityLinks) {
    this.repositoryEntityLinks = repositoryEntityLinks;
  }

  public StudentResourceAssembler() {
    super(StudentController.class, StudentResource.class);
  }

  @Override
  public StudentResource toResource(Student student) {
    StudentResource studentResource = instantiateResource(student);
    initResourceFields(studentResource, student);
    addSelfLinkToResource(studentResource, student);

    addEntityFieldLinkToResource(studentResource, student.getGroup().getId(), Group.class);
    addEntityFieldLinkToResource(studentResource, student.getEnglishLevel().getId(),
        EnglishLevel.class);

    return studentResource;
  }

  private void addCollectionFieldLinkToResource(StudentResource studentResource, Student student,
      String relationName) {
    Link collectionLink = repositoryEntityLinks
        .linkForSingleResource(Student.class, student.getId())
        .slash(relationName).withRel(relationName);
    studentResource.add(collectionLink);
  }

  private void addEntityFieldLinkToResource(StudentResource studentResource, int fieldId,
      Class clazz) {
    Link entityLink = repositoryEntityLinks
        .linkToSingleResource(clazz, fieldId);
    studentResource.add(entityLink);
  }

  private void addSelfLinkToResource(StudentResource studentResource, Student student) {
    Link studentLink = repositoryEntityLinks.linkToSingleResource(Student.class, student.getId());
    Link selfLink = new Link(studentLink.getHref(), Link.REL_SELF);
    studentResource.add(selfLink);
    studentResource.add(studentLink);
  }

  private void initResourceFields(StudentResource studentResource, Student student) {

    studentResource.setStudentId(student.getId());
    studentResource.setFirstName(student.getFirstName());
    studentResource.setLastName(student.getLastName());
    studentResource.setImage(student.getImage());
    studentResource.setCv(student.getCv());
  }
}
