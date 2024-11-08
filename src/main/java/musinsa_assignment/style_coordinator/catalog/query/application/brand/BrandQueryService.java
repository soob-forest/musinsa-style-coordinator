package musinsa_assignment.style_coordinator.catalog.query.application.brand;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.query.dao.BrandDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.CategoryDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.ProductDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandQueryService {

  private final BrandDao brandDao;
  private final ProductDao productDao;
  private final CategoryDao categoryDao;

  public CheapestBrandView getCheapestCategoryBundleView() {
    var cheapestBrand = brandDao.findCheapest();

    var cheapestCategoryViews = productDao.findByBrandId(cheapestBrand.getId()).stream().map(productData -> {
      var category = categoryDao.findById(productData.getCategoryId()).orElseThrow(NoSuchElementException::new);
      return new CheapestCategoryView(category.getType(), productData.getPrice());
    }).toList();

    return new CheapestBrandView(new CheapestCategoryBundleView(cheapestBrand, cheapestCategoryViews));
  }
}
