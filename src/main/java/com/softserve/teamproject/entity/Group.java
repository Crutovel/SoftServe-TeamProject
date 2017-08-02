package com.softserve.teamproject.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.teamproject.entity.deserializer.LocalDateDeserializer;
import com.softserve.teamproject.entity.deserializer.LocalDateSerializer;
import com.softserve.teamproject.entity.deserializer.LocationDeserializer;
import com.softserve.teamproject.entity.deserializer.StatusDeserializer;
import com.softserve.teamproject.entity.deserializer.UserDeserializer;
import com.softserve.teamproject.validation.StringConstraintInSet;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "educational_group")
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "name", unique = true)
  @Size(min = 4, max = 20)
  @Pattern(regexp = "[\\p{IsAlphabetic}\\p{IsWhite_Space}[0-9]-/]+")
  private String name;

  @ManyToMany
  @JoinTable(name = "group_teacher", joinColumns = {@JoinColumn(name = "group_id")},
      inverseJoinColumns = {@JoinColumn(name = "teacher_id")})
  @JsonDeserialize(using = UserDeserializer.class)
  private Set<User> teachers;

  @ManyToOne
  @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
  @NotNull
  @JsonDeserialize(using = LocationDeserializer.class)
  private Location location;

  @Column(name = "start_date", columnDefinition = "DATE")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate startDate;

  @Column(name = "finish_date", columnDefinition = "DATE")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate finishDate;

  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
  @JsonDeserialize(using = StatusDeserializer.class)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "specialization_id", referencedColumnName = "id", nullable = false)
  @NotNull
  private Specialization specialization;

  @ElementCollection
  @CollectionTable(name = "expert",
      joinColumns = @JoinColumn(name = "edu_group_id")
  )
  @Column(name = "expert_name")
  @StringConstraintInSet(min = 5, max = 25, regexp = "[\\p{IsAlphabetic}\\p{IsWhite_Space}-\\.]+")
  private Set<String> experts;

  @ManyToOne
  @JoinColumn(name = "budget_owner_id", referencedColumnName = "id", nullable = false)
  private BudgetOwner budgetOwner;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(LocalDate finishDate) {
    this.finishDate = finishDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Specialization getSpecialization() {
    return specialization;
  }

  public void setSpecialization(Specialization specialization) {
    this.specialization = specialization;
  }

  public Set<User> getTeachers() {
    return teachers;
  }

  public void setTeachers(Set<User> teachers) {
    this.teachers = teachers;
  }

  public Set<String> getExperts() {
    return experts;
  }

  public void setExperts(Set<String> experts) {
    this.experts = experts;
  }

  public BudgetOwner getBudgetOwner() {
    return budgetOwner;
  }

  public void setBudgetOwner(BudgetOwner budgetOwner) {
    this.budgetOwner = budgetOwner;
  }

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
    Group other = (Group) otherObject;
    return Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Group{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", teachers=" + teachers
        + ", location=" + location.getName()
        + ", startDate=" + startDate
        + ", finishDate=" + finishDate
        + ", status=" + status.getName()
        + ", specialization=" + specialization.getName()
        + ", budgetOwner=" + budgetOwner.getName()
        + '}';
  }
}
