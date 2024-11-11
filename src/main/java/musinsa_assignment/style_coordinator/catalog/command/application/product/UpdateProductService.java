package musinsa_assignment.style_coordinator.catalog.command.application.product;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandRepository;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryRepository;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final BrandRepository brandRepository;

  @Transactional
  public void update(String id, UpdateProductRequest request) {

    var product = productRepository.findById(ProductId.of(id)).orElseThrow(NoSuchElementException::new);

    var updateModelBuilder = product.toUpdateModelBuilder();

    var updateModel = request.toUpdateModel(updateModelBuilder);

    product.update(updateModel);

    // 업데이트 된 Product 검증
    categoryRepository.findById(product.getCategoryId()).orElseThrow(NoSuchElementException::new);
    brandRepository.findById(product.getBrandId()).orElseThrow(NoSuchElementException::new);
  }
}
