package musinsa_assignment.style_coordinator.catalog.query.application.product;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryType;
import musinsa_assignment.style_coordinator.catalog.query.dao.BrandDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.CategoryDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.ProductDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryService {

  private final CategoryDao categoryDao;
  private final ProductDao productDao;
  private final BrandDao brandDao;

  public MinAndMaxPriceProductView searchMinAndMaxProductsByCategory(String categoryName) {
    var category = categoryDao.findByType(CategoryType.findByName(categoryName)).orElseThrow(NoSuchElementException::new);
    var minPrice = productDao.findTop1ByCategoryIdOrderByPriceAsc(category.getId()).orElseThrow(NoSuchElementException::new).getPrice();
    // TODO 최소, 최대 가격의 상품을 찾는데 비효율적임. 개선 필요
    var minPriceProducts = productDao.findByPrice(minPrice).stream().map(
        productData -> new ProductSummary(brandDao.findById(productData.getBrandId()).orElseThrow(NoSuchElementException::new).getName(),
            productData.getPrice())).toList();

    var maxPrice = productDao.findTop1ByCategoryIdOrderByPriceDesc(category.getId()).orElseThrow(NoSuchElementException::new).getPrice();
    var maxPriceProducts = productDao.findByPrice(maxPrice).stream().map(
        productData -> new ProductSummary(brandDao.findById(productData.getBrandId()).orElseThrow(NoSuchElementException::new).getName(),
            productData.getPrice())).toList();

    return new MinAndMaxPriceProductView(categoryName, minPriceProducts, maxPriceProducts
    );
  }
}
