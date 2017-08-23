package com.softserve.teamproject.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softserve.teamproject.entity.deserializer.ExpertDeserializer;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "first_name")
  @Size(min = 2, max = 20, message = "Wrong size")
  @Pattern(regexp = "[\\p{IsAlphabetic}\\p{IsWhite_Space}-/]+", message = "Wrong pattern")
  private String firstName;

  @Column(name = "last_name")
  @Size(min = 2, max = 20, message = "Wrong size")
  @Pattern(regexp = "[\\p{IsAlphabetic}\\p{IsWhite_Space}-/]+", message = "Wrong pattern")
  private String lastName;

  @Lob
  @Column(name = "image")
  private byte[] image;

  @Column(name = "image_name")
  @Pattern(regexp = ".+(\\.jpg|\\.jpeg|\\.tiff|\\.png)",
      message = "{illegalArgs.student.cvOrImage.name}")
  private String imageName;

  @Lob
  @Column(name = "cv")
  private byte[] cv;

  @Column(name = "cv_name")
  @Pattern(regexp = ".+(\\.doc|\\.docx|\\.pdf|\\.rtf)",
      message = "{illegalArgs.student.cvOrImage.name}")
  private String cvName;

  @ManyToOne
  @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
  private Group group;

  @ManyToOne
  @JoinColumn(name = "english_level_id", referencedColumnName = "id")
  private EnglishLevel englishLevel;

  @Column(name="incoming_test")
  private Integer incomingTest;

  @Column(name="entry_score")
  private Double entryScore;

  @ManyToOne
  @JoinColumn(name = "approved_by_expert_id", referencedColumnName = "id", nullable = false)
  @JsonDeserialize(using = ExpertDeserializer.class)
  private Expert testApprovedByExpert;

  @Column(name = "teacher_score")
  private Integer teacherScore;

  @Column(name = "teacher_feedback")
  private String teacherFeedback;

  @Column(name = "expert_score")
  private Integer expertScore;

  @Column(name = "expert_feedback")
  private String expertFeedback;

  @Column(name = "interviewer_score")
  private Integer interviewerScore;

  @Column(name = "interviewer_feedback")
  private String interviewerFeedback;

  @Column(name = "test1_mark")
  private Integer test1Mark;

  @Column(name = "test2_mark")
  private Integer test2Mark;

  @Column(name = "test3_mark")
  private Integer test3Mark;

  @Column(name = "test4_mark")
  private Integer test4Mark;

  @Column(name = "test5_mark")
  private Integer test5Mark;

  @Column(name = "intermediate_bas_test")
  private Integer intermediateBasTest;

  @Column(name = "intermediate_language_test")
  private Integer intermediateLanguageTest;

  @Column(name = "final_bas_test")
  private Integer finalBasTest;

  @Column(name = "final_language_test")
  private Integer finalLanguageTest;

  @Column(name = "learning_ability_by_teacher")
  @Enumerated(EnumType.STRING)
  private LearningAbility learningAbilityByTeacher;

//  @Column(name = "learning_ability_by_expert")
//  @Enumerated(EnumType.STRING)
//  private LearningAbility learningAbilityByExpert;
//
//  @Column(name = "overall_technical_competence_by_teacher")
//  @Enumerated(EnumType.STRING)
//  private OverallTechnicalCompetence overallTechnicalCompetenceByTeacher;
//
//  @Column(name = "overall_technical_competence_by_expert")
//  @Enumerated(EnumType.STRING)
//  private OverallTechnicalCompetence overallTechnicalCompetenceByExpert;
//
//  @Column(name = "passional_initiative_by_teacher")
//  @Enumerated(EnumType.STRING)
//  private PassionalInitiative passionalInitiativeByTeacher;
//
//  @Column(name = "passional_initiative_by_expert")
//  @Enumerated(EnumType.STRING)
//  private PassionalInitiative passionalInitiativeByExpert;
//
//  @Column(name = "team_work_by_teacher")
//  @Enumerated(EnumType.STRING)
//  private TeamWork teamWorkByTeacher;
//
//  @Column(name = "team_work_by_expert")
//  @Enumerated(EnumType.STRING)
//  private TeamWork teamWorkByExpert;
//
//  @Column(name = "getting_things_done_by_teacher")
//  @Enumerated(EnumType.STRING)
//  private GettingThingsDone gettingThingsDoneByTeacher;
//
//  @Column(name = "getting_things_done_by_expert")
//  @Enumerated(EnumType.STRING)
//  private GettingThingsDone getGettingThingsDoneByExpert;
//
//  @Column(name = "active_communicator_by_teacher")
//  @Enumerated(EnumType.STRING)
//  private ActiveCommunicator activeCommunicatorByTeacher;
//
//  @Column(name = "active_communicator_by_expert")
//  @Enumerated(EnumType.STRING)
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

  public Expert getTestApprovedByExpert() {
    return testApprovedByExpert;
  }

  public void setTestApprovedByExpert(Expert testApprovedByExpert) {
    this.testApprovedByExpert = testApprovedByExpert;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public EnglishLevel getEnglishLevel() {
    return englishLevel;
  }

  public void setEnglishLevel(EnglishLevel englishLevel) {
    this.englishLevel = englishLevel;
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

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null) {
      return false;
    }
    if (getClass() != otherObject.getClass()) {
      return false;
    }
    Student other = (Student) otherObject;
    return Objects.equals(firstName, other.getFirstName()) && Objects
        .equals(lastName, other.getLastName()) && Objects
        .equals(group, other.group);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
  }

  @Override
  public String toString() {
    return "User{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", image=" + Arrays.toString(image)
        + ", group='" + group.getName() + '\''
        + ", englishLevel='" + englishLevel.getName() + '\''
        + '}';
  }
}
