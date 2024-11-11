package musinsa_assignment.style_coordinator.catalog.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductUpdateModel {

  private CategoryId categoryId;
  private BrandId brandId;
  private Money price;

  @Builder
  public ProductUpdateModel(String categoryId, String brandId, BigDecimal price) {

    this.categoryId = CategoryId.of(categoryId);
    this.brandId = BrandId.of(brandId);
    this.price = Money.of(price);
  }
}
