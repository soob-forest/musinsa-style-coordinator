package musinsa_assignment.style_coordinator.catalog.domain;

import java.util.Optional;
import musinsa_assignment.style_coordinator.common.domain.NanoIdGenerator;
import org.springframework.data.repository.Repository;

public interface BrandRepository extends Repository<Brand, BrandId> {

  Optional<Brand> findById(BrandId id);

  void save(Brand brand);

  void delete(Brand brand);

  default BrandId nextId() {
    IdGenerator idGenerator = new NanoIdGenerator();
    return BrandId.of(idGenerator.generateId());
  }
}
