package musinsa_assignment.style_coordinator.catalog.domain;

import java.util.List;
import musinsa_assignment.style_coordinator.common.domain.Money;

public interface ProductRankingService {

  List<ProductRankingData> findMinPriceRankingByCategoryId(CategoryId categoryId);

  List<ProductRankingData> findMinPriceRankingByBrandId(BrandId brandId);

  List<ProductRankingData> findMaxPriceRankingByCategoryId(CategoryId categoryId);

  void reRankForAdd(CategoryId categoryId, BrandId brandId, ProductId productId, Money price);
}
