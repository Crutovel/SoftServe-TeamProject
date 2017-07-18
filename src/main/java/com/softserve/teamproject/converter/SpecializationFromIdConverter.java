package com.softserve.teamproject.converter;

import com.softserve.teamproject.entity.Specialization;
import com.softserve.teamproject.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Retrieve specialization of group from database by it Id when Spring parse JSON field which
 * contains specialization id
 */
public class SpecializationFromIdConverter implements Converter<String, Specialization> {

  private SpecializationRepository specializationRepository;

  @Autowired
  public void setSpecializationRepository(
    SpecializationRepository specializationRepository) {
    this.specializationRepository = specializationRepository;
  }

  @Override
  public Specialization convert(String source) {
    return specializationRepository.findOne(Integer.valueOf(source));
  }
}
