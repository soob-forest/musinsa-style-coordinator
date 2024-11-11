package musinsa_assignment.style_coordinator.catalog.query.application.category;

import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryType;
import musinsa_assignment.style_coordinator.common.domain.Money;

public record CheapestProductBrandNameData(String categoryName, String brandName, BigDecimal price) {

  public CheapestProductBrandNameData(CategoryType categoryType, String brandName, Money price) {
    this(categoryType.getName(), brandName, price.getValue());
  }
}
