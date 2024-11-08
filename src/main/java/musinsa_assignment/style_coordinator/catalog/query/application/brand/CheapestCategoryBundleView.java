package musinsa_assignment.style_coordinator.catalog.query.application.brand;

import java.math.BigDecimal;
import java.util.List;
import musinsa_assignment.style_coordinator.catalog.query.dto.BrandData;

public record CheapestCategoryBundleView(String brandName, List<CheapestCategoryView> cheapestCategoryViewsViews, BigDecimal totalPrice) {

  public CheapestCategoryBundleView(BrandData brandData, List<CheapestCategoryView> cheapestCategoryViewsViews) {
    this(brandData.getName(), cheapestCategoryViewsViews,
        cheapestCategoryViewsViews.stream().map(CheapestCategoryView::price).reduce(BigDecimal.ZERO, BigDecimal::add));
  }
}


