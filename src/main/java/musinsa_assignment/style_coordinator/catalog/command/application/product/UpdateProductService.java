package musinsa_assignment.style_coordinator.catalog.command.application.product;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandRepository;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryRepository;
import musinsa_assignment.style_coordinator.catalog.domain.Product;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRankingService;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRepository;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoBrandException;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoCategoryException;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoProductException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final BrandRepository brandRepository;
  private final ProductRankingService productRankingService;

  @Transactional
  public void update(String id, UpdateProductRequest request) {

    var product = productRepository.findById(ProductId.of(id)).orElseThrow(NoProductException::new);

    var updateModelBuilder = product.toUpdateModelBuilder();
    var updateModel = request.toUpdateModel(updateModelBuilder);

    product.update(updateModel);
    validateUpdateProduct(product);
    productRankingService.reRankForAdd(product.getCategoryId(), product.getBrandId(), product.getId(), product.getPrice());
  }

  private void validateUpdateProduct(Product product) {
    categoryRepository.findById(product.getCategoryId())
        .orElseThrow(() -> new NoCategoryException("존재하지 않는 카테고리로 상품을 업데이트 할 수 없습니다."));
    brandRepository.findById(product.getBrandId()).orElseThrow(() -> new NoBrandException("존재하지 않는 브랜드로 상품을 업데이트 할 수 없습니다."));
  }
}
