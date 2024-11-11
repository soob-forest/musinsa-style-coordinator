package musinsa_assignment.style_coordinator.catalog.query.application.brand;

import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryType;
import musinsa_assignment.style_coordinator.common.domain.Money;

public record CheapestCategoryView(String categoryName, BigDecimal price) {

  public CheapestCategoryView(CategoryType categoryType, Money price) {
    this(categoryType.getName(), price.getValue());
  }

}
