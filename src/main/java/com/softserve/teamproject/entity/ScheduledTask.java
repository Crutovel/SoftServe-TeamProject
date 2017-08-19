package com.softserve.teamproject.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task_scheduler")
public class ScheduledTask {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne
  @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
  private Group group;

  @ManyToOne
  @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
  private Status updatedStatus;

  @Column(name = "time")
  private LocalDate dayOfUpdate;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public Status getUpdatedStatus() {
    return updatedStatus;
  }

  public void setUpdatedStatus(Status updatedStatus) {
    this.updatedStatus = updatedStatus;
  }

  public LocalDate getDayOfUpdate() {
    return dayOfUpdate;
  }

  public void setDayOfUpdate(LocalDate dayOfUpdate) {
    this.dayOfUpdate = dayOfUpdate;
  }
}
