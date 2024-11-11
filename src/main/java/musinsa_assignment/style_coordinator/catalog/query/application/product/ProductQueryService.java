package musinsa_assignment.style_coordinator.catalog.query.application.product;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryType;
import musinsa_assignment.style_coordinator.catalog.query.dao.BrandDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.CategoryDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.ProductDao;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoBrandException;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoCategoryException;
import musinsa_assignment.style_coordinator.ranking.query.RankingService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

  private final CategoryDao categoryDao;
  private final ProductDao productDao;
  private final BrandDao brandDao;

  private final RankingService rankingService;

  public MinAndMaxPriceProductView searchMinAndMaxProductsByCategory(String categoryName) {
    var category = categoryDao.findByType(CategoryType.findByName(categoryName)).orElseThrow(NoCategoryException::new);

    var minPriceRankingProducts = rankingService.findMinPriceRankingByCategoryId(category.getId());

    var minPriceProducts = minPriceRankingProducts.stream().map(
        product -> new ProductSummary(brandDao.findById(product.brandId()).orElseThrow(NoBrandException::new).getName(),
            product.price())
    ).toList();

    var maxPriceRankingProducts = rankingService.findMaxPriceRankingByCategoryId(category.getId());

    var maxPriceProducts = maxPriceRankingProducts.stream().map(
        product -> new ProductSummary(brandDao.findById(product.brandId()).orElseThrow(NoBrandException::new).getName(),
            product.price())
    ).toList();

    return new MinAndMaxPriceProductView(categoryName, minPriceProducts, maxPriceProducts);
  }
}
