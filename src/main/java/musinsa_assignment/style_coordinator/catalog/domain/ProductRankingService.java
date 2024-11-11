package musinsa_assignment.style_coordinator.catalog.domain;

import java.util.List;

public interface ProductRankingService {

  List<ProductRankingData> findMinPriceRankingByCategoryId(CategoryId categoryId);

  List<ProductRankingData> findMinPriceRankingByBrandId(BrandId brandId);
}
