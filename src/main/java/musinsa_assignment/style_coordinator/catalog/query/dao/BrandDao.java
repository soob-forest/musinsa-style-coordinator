package musinsa_assignment.style_coordinator.catalog.query.dao;

import java.util.Optional;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.query.dto.BrandData;
import org.springframework.data.repository.Repository;

public interface BrandDao extends Repository<BrandData, BrandId> {

  Optional<BrandData> findById(BrandId id);
}
