package musinsa_assignment.style_coordinator.catalog.command.application.product;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.Brand;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.BrandRepository;
import musinsa_assignment.style_coordinator.catalog.domain.Category;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryRepository;
import musinsa_assignment.style_coordinator.catalog.domain.Money;
import musinsa_assignment.style_coordinator.catalog.domain.Product;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRepository;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoBrandException;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoCategoryException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final BrandRepository brandRepository;

  @Transactional
  public ProductResponse create(ProductRequest productRequest) {

    Category category = categoryRepository.findById(CategoryId.of(productRequest.categoryId())).orElseThrow(NoCategoryException::new);
    Brand brand = brandRepository.findById(BrandId.of(productRequest.brandId())).orElseThrow(NoBrandException::new);

    var nextId = productRepository.nextId();
    var product = Product.builder()
        .id(nextId)
        .categoryId(category.getId())
        .brandId(brand.getId())
        .price(Money.of(productRequest.price()))
        .build();
    productRepository.save(product);

    return ProductResponse.from(product);
  }
}
