package musinsa_assignment.style_coordinator.ranking.query;

import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.common.domain.Money;
import musinsa_assignment.style_coordinator.ranking.domain.MaxPriceRanking;
import musinsa_assignment.style_coordinator.ranking.domain.MinPriceRanking;

public record PriceRankingData(CategoryId categoryId, BrandId brandId, ProductId productId, Money price) {

  public static PriceRankingData from(MinPriceRanking minPriceRanking) {
    return new PriceRankingData(minPriceRanking.getCategoryId(), minPriceRanking.getBrandId(), minPriceRanking.getProductId(),
        minPriceRanking.getPrice());
  }

  public static PriceRankingData from(MaxPriceRanking maxPriceRanking) {
    return new PriceRankingData(maxPriceRanking.getCategoryId(), maxPriceRanking.getBrandId(), maxPriceRanking.getProductId(),
        maxPriceRanking.getPrice());
  }
}
