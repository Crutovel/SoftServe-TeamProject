package com.softserve.teamproject.service.impl;

import com.softserve.teamproject.repository.GroupRepository;
import com.softserve.teamproject.repository.ScheduledTaskRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupScheduler implements Runnable {

  private GroupRepository groupRepository;
  private ScheduledTaskRepository schedulerRepo;
  private boolean firstRun = true;

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
    schedulerRepo.getActualTasks().forEach(task -> {
      task.getGroup().setStatus(task.getUpdatedStatus());
      groupRepository.save(task.getGroup());
      schedulerRepo.delete(task);
    });
    if (firstRun) {
      schedulerRepo.getOldTasks().forEach(task -> schedulerRepo.delete(task));
      firstRun = !firstRun;
    }
  }
}
