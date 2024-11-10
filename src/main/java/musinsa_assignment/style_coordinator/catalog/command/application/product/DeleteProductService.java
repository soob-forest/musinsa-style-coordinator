package musinsa_assignment.style_coordinator.catalog.command.application.product;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProductService {

  private final ProductRepository productRepository;

  @Transactional
  public void deleteByBrandId(BrandId brandId) {
    productRepository.deleteAllByBrandId(brandId);
  }
}
