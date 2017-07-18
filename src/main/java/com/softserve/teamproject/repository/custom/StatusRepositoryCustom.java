package com.softserve.teamproject.repository.custom;

import com.softserve.teamproject.entity.Status;

public interface StatusRepositoryCustom {

  Status getStatusByName(String name);
}
