package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.entity.Group;
import com.softserve.teamproject.entity.ScheduledTask;
import com.softserve.teamproject.entity.Status;
import com.softserve.teamproject.generator.StatusGenerator;
import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.ScheduledTaskRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupScheduler implements Runnable {

  private StatusGenerator generator;
  private GroupRepository groupRepository;
  private ScheduledTaskRepository schedulerRepo;
  private boolean firstRun = true;

  @Autowired
  public void setGenerator(StatusGenerator generator) {
    this.generator = generator;
  }

  @Autowired
  public GroupScheduler(GroupRepository groupRepository, ScheduledTaskRepository schedulerRepo) {
    this.groupRepository = groupRepository;
    this.schedulerRepo = schedulerRepo;
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.schedule(this, 10, TimeUnit.SECONDS);
    LocalDateTime nextScanning = LocalDate.now().plusDays(1).atTime(0, 0);
    scheduler.scheduleWithFixedDelay(
        this, LocalDateTime.now().until(nextScanning, ChronoUnit.SECONDS),
        TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
  }

  @Override
  public void run() {
    List<ScheduledTask> tasks = schedulerRepo.getOldTasks();
    tasks.sort(Comparator.naturalOrder());
    tasks.forEach(task -> {
      task.getGroup().setStatus(task.getUpdatedStatus());
      groupRepository.save(task.getGroup());
      schedulerRepo.delete(task);
    });
  }

  public void updateTasks(Group group) {
    Map<Status,LocalDate> template = generator.generateKeyEventTemplates(
        group.getSpecialization().getStrategy().getStatusTemplates(), group);
    clearTasks(group);
    template.forEach((status,date)->{
      if (status.getId() <= group.getStatus().getId()) {
        return;
      }
      ScheduledTask task = new ScheduledTask();
      task.setDayOfUpdate(date);
      task.setGroup(group);
      task.setUpdatedStatus(status);
      schedulerRepo.save(task);
    });
    run();
  }

  public void clearTasks(Group group) {
    schedulerRepo.delete(schedulerRepo.getTasksByGroupId(group));
  }
}
