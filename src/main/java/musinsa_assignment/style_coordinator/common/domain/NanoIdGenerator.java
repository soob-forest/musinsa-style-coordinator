package musinsa_assignment.style_coordinator.common.domain;

import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.randomNanoId;

import musinsa_assignment.style_coordinator.catalog.domain.IdGenerator;


public class NanoIdGenerator implements IdGenerator {

  @Override
  public String generateId() {
    return randomNanoId();
  }
}
