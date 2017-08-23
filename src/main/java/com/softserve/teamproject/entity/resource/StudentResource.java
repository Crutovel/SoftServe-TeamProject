package com.softserve.teamproject.entity.resource;

import com.softserve.teamproject.entity.ActiveCommunicator;
import com.softserve.teamproject.entity.Expert;
import com.softserve.teamproject.entity.GettingThingsDone;
import com.softserve.teamproject.entity.LearningAbility;
import com.softserve.teamproject.entity.OverallTechnicalCompetence;
import com.softserve.teamproject.entity.PassionalInitiative;
import com.softserve.teamproject.entity.TeamWork;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

/**
 * Use for represent Student object in http response in HATEOAS style.
 */
@Component
public class StudentResource extends ResourceSupport {

  private int studentId;

  private String firstName;

  private String lastName;

  private byte[] image;

  private String imageName;

  private byte[] cv;

  private String cvName;

  private Integer incomingTest;

  private Double entryScore;

  private Integer teacherScore;

  private String teacherFeedback;

  private Integer expertScore;

  private String expertFeedback;

  private Integer interviewerScore;

  private String interviewerFeedback;

  private Integer test1Mark;

  private Integer test2Mark;

  private Integer test3Mark;

  private Integer test4Mark;

  private Integer test5Mark;

  private Integer intermediateBasTest;

  private Integer intermediateLanguageTest;

  private Integer finalBasTest;

  private Integer finalLanguageTest;

  private LearningAbility learningAbilityByTeacher;

//  private LearningAbility learningAbilityByExpert;
//
//  private OverallTechnicalCompetence overallTechnicalCompetenceByTeacher;
//
//  private OverallTechnicalCompetence overallTechnicalCompetenceByExpert;
//
//  private PassionalInitiative passionalInitiativeByTeacher;
//
//  private PassionalInitiative passionalInitiativeByExpert;
//
//  private TeamWork teamWorkByTeacher;
//
//  private TeamWork teamWorkByExpert;
//
//  private GettingThingsDone gettingThingsDoneByTeacher;
//
//  private GettingThingsDone getGettingThingsDoneByExpert;
//
//  private ActiveCommunicator activeCommunicatorByTeacher;
//
//  private ActiveCommunicator activeCommunicatorByExpert;

  public Integer getIncomingTest() {
    return incomingTest;
  }

  public void setIncomingTest(Integer incomingTest) {
    this.incomingTest = incomingTest;
  }

  public Double getEntryScore() {
    return entryScore;
  }

  public void setEntryScore(Double entryScore) {
    this.entryScore = entryScore;
  }

  public int getStudentId() {
    return studentId;
  }

  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public byte[] getCv() {
    return cv;
  }

  public void setCv(byte[] cv) {
    this.cv = cv;
  }

  public String getCvName() {
    return cvName;
  }

  public void setCvName(String cvName) {
    this.cvName = cvName;
  }

  public Integer getTeacherScore() {
    return teacherScore;
  }

  public void setTeacherScore(Integer teacherScore) {
    this.teacherScore = teacherScore;
  }

  public String getTeacherFeedback() {
    return teacherFeedback;
  }

  public void setTeacherFeedback(String teacherFeedback) {
    this.teacherFeedback = teacherFeedback;
  }

  public Integer getExpertScore() {
    return expertScore;
  }

  public void setExpertScore(Integer expertScore) {
    this.expertScore = expertScore;
  }

  public String getExpertFeedback() {
    return expertFeedback;
  }

  public void setExpertFeedback(String expertFeedback) {
    this.expertFeedback = expertFeedback;
  }

  public Integer getInterviewerScore() {
    return interviewerScore;
  }

  public void setInterviewerScore(Integer interviewerScore) {
    this.interviewerScore = interviewerScore;
  }

  public String getInterviewerFeedback() {
    return interviewerFeedback;
  }

  public void setInterviewerFeedback(String interviewerFeedback) {
    this.interviewerFeedback = interviewerFeedback;
  }

  public Integer getTest1Mark() {
    return test1Mark;
  }

  public void setTest1Mark(Integer test1Mark) {
    this.test1Mark = test1Mark;
  }

  public Integer getTest2Mark() {
    return test2Mark;
  }

  public void setTest2Mark(Integer test2Mark) {
    this.test2Mark = test2Mark;
  }

  public Integer getTest3Mark() {
    return test3Mark;
  }

  public void setTest3Mark(Integer test3Mark) {
    this.test3Mark = test3Mark;
  }

  public Integer getTest4Mark() {
    return test4Mark;
  }

  public void setTest4Mark(Integer test4Mark) {
    this.test4Mark = test4Mark;
  }

  public Integer getTest5Mark() {
    return test5Mark;
  }

  public void setTest5Mark(Integer test5Mark) {
    this.test5Mark = test5Mark;
  }

  public Integer getIntermediateBasTest() {
    return intermediateBasTest;
  }

  public void setIntermediateBasTest(Integer intermediateBasTest) {
    this.intermediateBasTest = intermediateBasTest;
  }

  public Integer getIntermediateLanguageTest() {
    return intermediateLanguageTest;
  }

  public void setIntermediateLanguageTest(Integer intermediateLanguageTest) {
    this.intermediateLanguageTest = intermediateLanguageTest;
  }

  public Integer getFinalBasTest() {
    return finalBasTest;
  }

  public void setFinalBasTest(Integer finalBasTest) {
    this.finalBasTest = finalBasTest;
  }

  public Integer getFinalLanguageTest() {
    return finalLanguageTest;
  }

  public void setFinalLanguageTest(Integer finalLanguageTest) {
    this.finalLanguageTest = finalLanguageTest;
  }

  public LearningAbility getLearningAbilityByTeacher() {
    return learningAbilityByTeacher;
  }

  public void setLearningAbilityByTeacher(
      LearningAbility learningAbilityByTeacher) {
    this.learningAbilityByTeacher = learningAbilityByTeacher;
  }

//  public LearningAbility getLearningAbilityByExpert() {
//    return learningAbilityByExpert;
//  }
//
//  public void setLearningAbilityByExpert(
//      LearningAbility learningAbilityByExpert) {
//    this.learningAbilityByExpert = learningAbilityByExpert;
//  }
//
//  public OverallTechnicalCompetence getOverallTechnicalCompetenceByTeacher() {
//    return overallTechnicalCompetenceByTeacher;
//  }
//
//  public void setOverallTechnicalCompetenceByTeacher(
//      OverallTechnicalCompetence overallTechnicalCompetenceByTeacher) {
//    this.overallTechnicalCompetenceByTeacher = overallTechnicalCompetenceByTeacher;
//  }
//
//  public OverallTechnicalCompetence getOverallTechnicalCompetenceByExpert() {
//    return overallTechnicalCompetenceByExpert;
//  }
//
//  public void setOverallTechnicalCompetenceByExpert(
//      OverallTechnicalCompetence overallTechnicalCompetenceByExpert) {
//    this.overallTechnicalCompetenceByExpert = overallTechnicalCompetenceByExpert;
//  }
//
//  public PassionalInitiative getPassionalInitiativeByTeacher() {
//    return passionalInitiativeByTeacher;
//  }
//
//  public void setPassionalInitiativeByTeacher(
//      PassionalInitiative passionalInitiativeByTeacher) {
//    this.passionalInitiativeByTeacher = passionalInitiativeByTeacher;
//  }
//
//  public PassionalInitiative getPassionalInitiativeByExpert() {
//    return passionalInitiativeByExpert;
//  }
//
//  public void setPassionalInitiativeByExpert(
//      PassionalInitiative passionalInitiativeByExpert) {
//    this.passionalInitiativeByExpert = passionalInitiativeByExpert;
//  }
//
//  public TeamWork getTeamWorkByTeacher() {
//    return teamWorkByTeacher;
//  }
//
//  public void setTeamWorkByTeacher(TeamWork teamWorkByTeacher) {
//    this.teamWorkByTeacher = teamWorkByTeacher;
//  }
//
//  public TeamWork getTeamWorkByExpert() {
//    return teamWorkByExpert;
//  }
//
//  public void setTeamWorkByExpert(TeamWork teamWorkByExpert) {
//    this.teamWorkByExpert = teamWorkByExpert;
//  }
//
//  public GettingThingsDone getGettingThingsDoneByTeacher() {
//    return gettingThingsDoneByTeacher;
//  }
//
//  public void setGettingThingsDoneByTeacher(
//      GettingThingsDone gettingThingsDoneByTeacher) {
//    this.gettingThingsDoneByTeacher = gettingThingsDoneByTeacher;
//  }
//
//  public GettingThingsDone getGetGettingThingsDoneByExpert() {
//    return getGettingThingsDoneByExpert;
//  }
//
//  public void setGetGettingThingsDoneByExpert(
//      GettingThingsDone getGettingThingsDoneByExpert) {
//    this.getGettingThingsDoneByExpert = getGettingThingsDoneByExpert;
//  }
//
//  public ActiveCommunicator getActiveCommunicatorByTeacher() {
//    return activeCommunicatorByTeacher;
//  }
//
//  public void setActiveCommunicatorByTeacher(
//      ActiveCommunicator activeCommunicatorByTeacher) {
//    this.activeCommunicatorByTeacher = activeCommunicatorByTeacher;
//  }
//
//  public ActiveCommunicator getActiveCommunicatorByExpert() {
//    return activeCommunicatorByExpert;
//  }
//
//  public void setActiveCommunicatorByExpert(
//      ActiveCommunicator activeCommunicatorByExpert) {
//    this.activeCommunicatorByExpert = activeCommunicatorByExpert;
//  }
}
