package musinsa_assignment.style_coordinator.catalog.query.application.category;

import java.math.BigDecimal;
import java.util.List;

public record CheapestPerCategoryView(List<CheapestProductBrandNameData> contents, BigDecimal totalPrice) {

  public CheapestPerCategoryView(List<CheapestProductBrandNameData> contents) {
    this(contents, contents.stream().map(CheapestProductBrandNameData::price).reduce(BigDecimal.ZERO, BigDecimal::add));
  }
}
