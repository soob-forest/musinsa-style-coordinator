package musinsa_assignment.style_coordinator.catalog.command.application.brand;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.command.application.product.DeleteProductService;
import musinsa_assignment.style_coordinator.catalog.domain.Brand;
import musinsa_assignment.style_coordinator.catalog.domain.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBrandService {

  private final BrandRepository brandRepository;
  private final DeleteProductService deleteProductService;

  @Transactional
  public BrandResponse create(BrandRequest brandRequest) {
    var nextId = brandRepository.nextId();
    brandRepository.save(
        Brand.builder()
            .id(nextId)
            .name(brandRequest.name())
            .build()
    );
    return new BrandResponse(nextId.getValue(), brandRequest.name());
  }
}
