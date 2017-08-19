package com.softserve.teamproject;

import com.softserve.teamproject.service.impl.GroupScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OnStartUp implements CommandLineRunner{


  @Autowired
  GroupScheduler scheduler;

  @Override
  public void run(String... strings) throws Exception {
//    scheduler.start();
  }
}
