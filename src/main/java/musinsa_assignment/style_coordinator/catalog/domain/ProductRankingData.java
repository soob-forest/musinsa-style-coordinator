package musinsa_assignment.style_coordinator.catalog.domain;

import musinsa_assignment.style_coordinator.common.domain.Money;
import musinsa_assignment.style_coordinator.ranking.query.PriceRankingData;

public record ProductRankingData(CategoryId categoryId, BrandId brandId, ProductId productId, Money price) {

  public static ProductRankingData from(PriceRankingData priceRankingData) {
    return new ProductRankingData(priceRankingData.categoryId(), priceRankingData.brandId(), priceRankingData.productId(), priceRankingData.price());
  }
}
