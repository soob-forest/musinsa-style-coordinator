package musinsa_assignment.style_coordinator.catalog.query.application.product;

import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.common.domain.Money;

public record ProductSummary(String brandName, BigDecimal price) {

  public ProductSummary(String brandName, Money money) {
    this(brandName, money.getValue());
  }
}
