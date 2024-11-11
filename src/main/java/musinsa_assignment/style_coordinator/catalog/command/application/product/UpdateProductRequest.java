package musinsa_assignment.style_coordinator.catalog.command.application.product;

import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.domain.ProductUpdateModel;
import musinsa_assignment.style_coordinator.catalog.domain.ProductUpdateModel.ProductUpdateModelBuilder;

public record UpdateProductRequest(String categoryId, String brandId, BigDecimal price) {

  public ProductUpdateModel toUpdateModel(ProductUpdateModelBuilder builder) {
    if (categoryId != null) {
      builder.categoryId(categoryId);
    }
    if (brandId != null) {
      builder.brandId(brandId);
    }
    if (price != null) {
      builder.price(price);
    }
    return builder.build();
  }
}
