package musinsa_assignment.style_coordinator.catalog.infra;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.BrandProductsDeleteService;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandProductsDeleteServiceImpl implements BrandProductsDeleteService {

  private final ProductRepository productRepository;

  @Override
  public void deleteProductsInBrand(BrandId brandId) {
    productRepository.deleteAllByBrandId(brandId);
  }
}
