package musinsa_assignment.style_coordinator.catalog.command.application.brand;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.Brand;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.BrandProductsDeleteService;
import musinsa_assignment.style_coordinator.catalog.domain.BrandRepository;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoBrandException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteBrandService {

  private final BrandRepository brandRepository;
  private final BrandProductsDeleteService brandProductsDeleteService;

  @Transactional
  public void delete(String id) {
    Brand brand = brandRepository.findById(BrandId.of(id)).orElseThrow(NoBrandException::new);
    brandRepository.delete(brand);

    brandProductsDeleteService.deleteProductsInBrand(brand.getId());
  }
}
