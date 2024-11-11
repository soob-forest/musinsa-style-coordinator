package musinsa_assignment.style_coordinator.catalog.command.application.brand;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.Brand;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.BrandRepository;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoBrandException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateBrandService {

  private final BrandRepository brandRepository;

  @Transactional
  public void updateName(String id, BrandRequest brandRequest) {
    Brand brand = brandRepository.findById(BrandId.of(id)).orElseThrow(NoBrandException::new);

    brand.updateName(brandRequest.name());
  }
}
