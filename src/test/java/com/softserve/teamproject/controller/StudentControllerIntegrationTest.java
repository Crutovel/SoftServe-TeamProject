package com.softserve.teamproject.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.teamproject.TestData;
import com.softserve.teamproject.entity.Student;
import com.softserve.teamproject.service.TestStudent;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class StudentControllerIntegrationTest {

  private final String STUDENT = "Myst1c";
  private final String TEACHER_WITHOUT_GROUPS = "NogroupsUser";
  private final String TEACHER_WITH_GROUPS = "OlegShvets";
  private final String COORDINATOR = "DmytroPetin";
  @Autowired
  private MockMvc mvc;

  @TestStudent
  @WithUserDetails(STUDENT)
  @Test
  public void getStudents_returnAllStudentsByStudent_returnForbidden() throws Exception {
    //Arrange
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void getStudents_returnAllStudents() throws Exception {
    //Arrange
    final String[] EXPECTED_STUDENT_NAMES = {"Roy", "Dmytro", "Maksym", "Lev", "Motoko", "Ace"};
    final int RESULT_SET_SIZE = 6;
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)))
        .andExpect(jsonPath("$[*].firstName", containsInAnyOrder(EXPECTED_STUDENT_NAMES)));
  }

  @TestStudent
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void getStudents_byTeacherWithGroups_returnAllStudents() throws Exception {
    //Arrange
    final String[] EXPECTED_STUDENT_NAMES = {"Roy", "Dmytro", "Maksym", "Lev", "Motoko", "Ace"};
    final int RESULT_SET_SIZE = 6;
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)))
        .andExpect(jsonPath("$[*].firstName", containsInAnyOrder(EXPECTED_STUDENT_NAMES)));
  }

  @TestStudent
  @WithUserDetails(TEACHER_WITHOUT_GROUPS)
  @Test
  public void getStudents_byTeacherWithoutGroups_returnAllStudents() throws Exception {
    //Arrange
    final String[] EXPECTED_STUDENT_NAMES = {"Roy", "Dmytro", "Maksym", "Lev", "Motoko", "Ace"};
    final int RESULT_SET_SIZE = 6;
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)))
        .andExpect(jsonPath("$[*].firstName", containsInAnyOrder(EXPECTED_STUDENT_NAMES)));
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void getStudents_ByGroupId_returnStudents() throws Exception {
    //Arrange
    final String[] EXPECTED_STUDENT_NAMES = {"Roy", "Motoko", "Ace"};
    final String GROUP_REQUEST_PARAM = "3";
    final int RESULT_SET_SIZE = 3;
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .param("groupid", GROUP_REQUEST_PARAM)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)))
        .andExpect(jsonPath("$[*].firstName", containsInAnyOrder(EXPECTED_STUDENT_NAMES)));
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void getStudents_ByGroupIdWithNoStudents_returnEmptyResult() throws Exception {
    //Arrange
    final String GROUP_REQUEST_PARAM = "4";
    final int RESULT_SET_SIZE = 0;
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .param("groupid", GROUP_REQUEST_PARAM)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)));

  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void getStudents_ByNotExistedGroupId_returnEmptyResult() throws Exception {
    //Arrange
    final String GROUP_REQUEST_PARAM = "32432";
    final int RESULT_SET_SIZE = 0;
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(get(TESTED_URL)
        .param("groupid", GROUP_REQUEST_PARAM)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(RESULT_SET_SIZE)));
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void addStudents_oneValidStudent_returnStudentResource() throws Exception {
    //Arrange
    final String NEW_STUDENT_FIRST_NAME = "Jim";
    List<Student> students = new ArrayList<>();
    students.add(TestData.getStudent(NEW_STUDENT_FIRST_NAME));
    final Integer GROUP_ID = 2;
    final String TESTED_URL = "/groups/{id}/students";
    //Act && Assert
    mvc.perform(post(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName", is(NEW_STUDENT_FIRST_NAME)));
  }

  @TestStudent
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void addStudents_oneValidStudentByTeacherWithGroups_returnForbidden() throws Exception {
    //Arrange
    final String NEW_STUDENT_FIRST_NAME = "Jim";
    List<Student> students = new ArrayList<>();
    students.add(TestData.getStudent(NEW_STUDENT_FIRST_NAME));
    final Integer GROUP_ID = 2;
    final String TESTED_URL = "/groups/{id}/students";
    //Act && Assert
    mvc.perform(post(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isForbidden());
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void addStudents_twoValidStudents_returnArrayStudentsResources() throws Exception {
    //Arrange
    String[] studentsNames = {"Jim", "Stanley"};

    List<Student> students = new ArrayList<>();
    students.add(TestData.getStudent(studentsNames[0]));
    students.add(TestData.getStudent(studentsNames[1]));
    final Integer GROUP_ID = 2;
    final String TESTED_URL = "/groups/{id}/students";
    //Act && Assert
    mvc.perform(post(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].firstName", everyItem(Matchers.isIn(studentsNames))));
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void addStudents_OneValidAndOneInvalidStudents_returnBadRequest() throws Exception {
    //Arrange
    String[] studentsNames = {"", "Stanley"};

    List<Student> students = new ArrayList<>();
    students.add(TestData.getStudent(studentsNames[0]));
    students.add(TestData.getStudent(studentsNames[1]));
    final Integer GROUP_ID = 2;
    final String TESTED_URL = "/groups/{id}/students";
    //Act && Assert
    mvc.perform(post(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isBadRequest());
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void addStudents_oneNotValidFirstNameStudent_returnBadRequest() throws Exception {
    //Arrange
    final String NEW_STUDENT_FIRST_NAME = "";
    List<Student> students = new ArrayList<>();
    students.add(TestData.getStudent(NEW_STUDENT_FIRST_NAME));
    final Integer GROUP_ID = 2;
    final String TESTED_URL = "/groups/{id}/students";
    //Act && Assert
    mvc.perform(post(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isBadRequest());
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void addStudents_oneNotValidLastNameStudent_returnBadRequest() throws Exception {
    //Arrange
    final String NEW_STUDENT_FIRST_NAME = "Jim";
    List<Student> students = new ArrayList<>();
    Student newStudent = TestData.getStudent(NEW_STUDENT_FIRST_NAME);
    newStudent.setLastName("");
    students.add(newStudent);
    final Integer GROUP_ID = 2;
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(put(TESTED_URL, GROUP_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isBadRequest());
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void editStudents_editOneValidStudent_returnStudentResource() throws Exception {
    //Arrange
    final String EDITED_STUDENT_FIRST_NAME = "Jim";
    List<Student> students = new ArrayList<>();
    Student newStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAME);
    newStudent.setId(3);
    students.add(newStudent);
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(put(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName", is(EDITED_STUDENT_FIRST_NAME)));
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void editStudents_editTwoValidStudents_returnStudentResource() throws Exception {
    //Arrange
    String[] EDITED_STUDENT_FIRST_NAMES = {"Jim", "Stanley"};

    List<Student> students = new ArrayList<>();
    Student oneStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAMES[0]);
    Student twoStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAMES[1]);
    oneStudent.setId(2);
    twoStudent.setId(3);
    students.add(oneStudent);
    students.add(twoStudent);

    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(put(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*].firstName",
            containsInAnyOrder(EDITED_STUDENT_FIRST_NAMES)));
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void editStudents_editOneValidAndOneInvalidStudents_returnBadRequest() throws Exception {
    //Arrange
    String[] EDITED_STUDENT_FIRST_NAMES = {"", "Stanley"};

    List<Student> students = new ArrayList<>();
    Student oneStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAMES[0]);
    Student twoStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAMES[1]);
    oneStudent.setId(2);
    twoStudent.setId(3);
    students.add(oneStudent);
    students.add(twoStudent);

    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(put(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isBadRequest());
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void editStudents_editOneValidAndOneAlienStudent_returnForbidden() throws Exception {
    //Arrange
    String[] EDITED_STUDENT_FIRST_NAMES = {"Jim", "Stanley"};

    List<Student> students = new ArrayList<>();
    Student oneStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAMES[0]);
    Student twoStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAMES[1]);
    oneStudent.setId(2);
    twoStudent.setId(6);
    students.add(oneStudent);
    students.add(twoStudent);

    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(put(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isForbidden());
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void editStudents_editNotValidStudent_returnBadRequest() throws Exception {
    //Arrange
    final String EDITED_STUDENT_FIRST_NAME = "";
    List<Student> students = new ArrayList<>();
    Student newStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAME);
    newStudent.setId(3);
    students.add(newStudent);
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(put(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isBadRequest());
  }

  @TestStudent
  @WithUserDetails(COORDINATOR)
  @Test
  public void editStudents_editOneValidAndStudentInAlienLocation_returnForbidden()
      throws Exception {
    //Arrange
    final String EDITED_STUDENT_FIRST_NAME = "Jim";
    List<Student> students = new ArrayList<>();
    Student newStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAME);
    newStudent.setId(6);
    students.add(newStudent);
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(put(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isForbidden());
  }

  @TestStudent
  @WithUserDetails(TEACHER_WITH_GROUPS)
  @Test
  public void editStudents_editOneValidStudentByTeacherWithGroups_returnForbidden()
      throws Exception {
    //Arrange
    final String EDITED_STUDENT_FIRST_NAME = "Jim";
    List<Student> students = new ArrayList<>();
    Student newStudent = TestData.getStudent(EDITED_STUDENT_FIRST_NAME);
    newStudent.setId(3);
    students.add(newStudent);
    final String TESTED_URL = "/students";
    //Act && Assert
    mvc.perform(put(TESTED_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(students)))
        .andExpect(status().isForbidden());
  }

}
