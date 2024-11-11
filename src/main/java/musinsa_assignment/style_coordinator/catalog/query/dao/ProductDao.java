package musinsa_assignment.style_coordinator.catalog.query.dao;

import java.util.List;
import java.util.Optional;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.query.dto.ProductData;
import musinsa_assignment.style_coordinator.common.domain.Money;
import org.springframework.data.repository.Repository;

public interface ProductDao extends Repository<ProductData, ProductId> {

  Optional<ProductData> findById(ProductId productId);

  Optional<ProductData> findTop1ByCategoryIdOrderByPriceAsc(CategoryId categoryId);

  Optional<ProductData> findTop1ByCategoryIdOrderByPriceDesc(CategoryId categoryId);

  List<ProductData> findByBrandId(BrandId id);

  List<ProductData> findByPrice(Money price);
}
