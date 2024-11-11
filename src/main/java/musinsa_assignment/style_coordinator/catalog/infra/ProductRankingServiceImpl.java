package musinsa_assignment.style_coordinator.catalog.infra;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRankingData;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRankingService;
import musinsa_assignment.style_coordinator.ranking.query.RankingService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductRankingServiceImpl implements ProductRankingService {

  private final RankingService rankingService;

  @Override
  public List<ProductRankingData> findMinPriceRankingByCategoryId(CategoryId categoryId) {
    var minPriceProducts = rankingService.findMinPriceRankingByCategoryId(categoryId);
    return minPriceProducts.stream().map(ProductRankingData::from).collect(Collectors.toList());
  }

  @Override
  public List<ProductRankingData> findMinPriceRankingByBrandId(BrandId brandId) {
    var minPriceProducts = rankingService.findMinPriceRankingByBrandId(brandId);
    return minPriceProducts.stream().map(ProductRankingData::from).collect(Collectors.toList());
  }

  @Override
  public List<ProductRankingData> findMaxPriceRankingByCategoryId(CategoryId categoryId) {
    var maxPriceProducts = rankingService.findMaxPriceRankingByCategoryId(categoryId);  
    return maxPriceProducts.stream().map(ProductRankingData::from).collect(Collectors.toList());
  }
}
