package musinsa_assignment.style_coordinator.catalog.command.application.product;

import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.domain.Product;

public record ProductResponse(String id, String categoryId, String brandId, BigDecimal price) {

  public static ProductResponse from(Product product) {
    return new ProductResponse(product.getId().getValue(), product.getCategoryId().getValue(), product.getBrandId().getValue(),
        product.getPrice().getValue());
  }

}
