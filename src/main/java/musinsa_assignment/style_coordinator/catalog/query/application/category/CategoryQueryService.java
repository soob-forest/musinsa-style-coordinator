package musinsa_assignment.style_coordinator.catalog.query.application.category;

import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRankingService;
import musinsa_assignment.style_coordinator.catalog.query.dao.BrandDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.CategoryDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.ProductDao;
import musinsa_assignment.style_coordinator.catalog.query.dto.BrandData;
import musinsa_assignment.style_coordinator.catalog.query.dto.CategoryData;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoBrandException;
import musinsa_assignment.style_coordinator.catalog.query.exception.NoProductException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryQueryService {

  private final CategoryDao categoryDao;
  private final ProductDao productDao;
  private final BrandDao brandDao;

  private final ProductRankingService productRankingService;

  // TODO 최저 가격의 product이 하나가 아닐 경우 어떻게 할지 구현필요
  public CheapestPerCategoryView getCheapestPerCategoryView() {
    return new CheapestPerCategoryView(
        categoryDao.findAll().stream()
            .sorted(sortByExposureOrder())
            .map(categoryData -> {

              var minPriceProductsByCategory = productRankingService.findMinPriceRankingByCategoryId(categoryData.getId());

              var selectedProduct = minPriceProductsByCategory.stream()
                  .filter(categoryProduct -> {
                    var minPriceProductsByBrand = productRankingService.findMinPriceRankingByBrandId(categoryProduct.brandId());
                    return minPriceProductsByBrand.stream()
                        .noneMatch(brandProduct -> brandProduct.productId().equals(categoryProduct.productId()));
                  })
                  .findFirst()
                  .orElse(minPriceProductsByCategory.get(0));

              BrandData brandData = brandDao.findById(selectedProduct.brandId())
                  .orElseThrow(NoBrandException::new);

              var product = productDao.findById(selectedProduct.productId()).orElseThrow(NoProductException::new);

              return new CheapestProductBrandNameData(categoryData.getType(), brandData.getName(), product.getPrice());
            }).toList()

    );
  }

  private static Comparator<CategoryData> sortByExposureOrder() {
    return (a, b) -> {
      var aOrdinal = a.getType().getExposureOrder();
      var bOrdinal = b.getType().getExposureOrder();
      return Integer.compare(aOrdinal, bOrdinal);
    };
  }

}
