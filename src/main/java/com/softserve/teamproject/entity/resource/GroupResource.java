package com.softserve.teamproject.entity.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.teamproject.entity.deserializer.LocalDateSerializer;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

/**
 * Use for represent Group object in http response in HATEOAS style.
 */
@Component
public class GroupResource extends ResourceSupport {

  private int groupId;

  private String name;

  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate startDate;

  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate finishDate;

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    GroupResource that = (GroupResource) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
