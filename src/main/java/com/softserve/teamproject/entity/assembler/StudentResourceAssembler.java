package com.softserve.teamproject.entity.assembler;

import com.softserve.teamproject.controller.StudentController;
import com.softserve.teamproject.entity.EnglishLevel;
import com.softserve.teamproject.entity.Expert;
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
    addEntityFieldLinkToResource(studentResource, student.getTestApprovedByExpert().getId(),
        Expert.class);

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
    studentResource.setImageName(student.getImageName());
    studentResource.setCv(student.getCv());
    studentResource.setCvName(student.getCvName());
    studentResource.setEntryScore(student.getEntryScore());
    studentResource.setIncomingTest(student.getIncomingTest());
    studentResource.setTeacherScore(student.getTeacherScore());
    studentResource.setTeacherFeedback(student.getTeacherFeedback());
    studentResource.setExpertScore(student.getExpertScore());
    studentResource.setExpertFeedback(student.getExpertFeedback());
    studentResource.setInterviewerScore(student.getInterviewerScore());
    studentResource.setInterviewerFeedback(student.getInterviewerFeedback());
    studentResource.setTest1Mark(student.getTest1Mark());
    studentResource.setTest2Mark(student.getTest2Mark());
    studentResource.setTest3Mark(student.getTest3Mark());
    studentResource.setTest4Mark(student.getTest4Mark());
    studentResource.setTest5Mark(student.getTest5Mark());
    studentResource.setIntermediateBasTest(student.getIntermediateBasTest());
    studentResource.setIntermediateLanguageTest(student.getIntermediateLanguageTest());
    studentResource.setFinalBasTest(student.getFinalBasTest());
    studentResource.setFinalLanguageTest(student.getFinalLanguageTest());
    studentResource.setLearningAbilityByTeacher(student.getLearningAbilityByTeacher());
//    studentResource.setLearningAbilityByExpert(student.getLearningAbilityByExpert());
//    studentResource
//        .setOverallTechnicalCompetenceByTeacher(student.getOverallTechnicalCompetenceByTeacher());
//    studentResource
//        .setOverallTechnicalCompetenceByExpert(student.getOverallTechnicalCompetenceByExpert());
//    studentResource.setPassionalInitiativeByTeacher(student.getPassionalInitiativeByTeacher());
//    studentResource.setPassionalInitiativeByExpert(student.getPassionalInitiativeByExpert());
//    studentResource.setTeamWorkByTeacher(student.getTeamWorkByTeacher());
//    studentResource.setTeamWorkByExpert(student.getTeamWorkByExpert());
//    studentResource.setGettingThingsDoneByTeacher(student.getGettingThingsDoneByTeacher());
//    studentResource.setGetGettingThingsDoneByExpert(student.getGetGettingThingsDoneByExpert());
//    studentResource.setActiveCommunicatorByTeacher(student.getActiveCommunicatorByTeacher());
//    studentResource.setActiveCommunicatorByExpert(student.getActiveCommunicatorByExpert());

  }
}
