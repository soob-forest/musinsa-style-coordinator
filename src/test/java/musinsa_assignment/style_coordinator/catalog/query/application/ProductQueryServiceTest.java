package musinsa_assignment.style_coordinator.catalog.query.application;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryType;
import musinsa_assignment.style_coordinator.catalog.domain.Money;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.query.application.product.ProductQueryService;
import musinsa_assignment.style_coordinator.catalog.query.dao.BrandDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.CategoryDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.ProductDao;
import musinsa_assignment.style_coordinator.catalog.query.dto.BrandData;
import musinsa_assignment.style_coordinator.catalog.query.dto.CategoryData;
import musinsa_assignment.style_coordinator.catalog.query.dto.ProductData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ProductQueryServiceTest {

  @MockBean
  private CategoryDao categoryDao;
  @MockBean
  private ProductDao productDao;
  @MockBean
  private BrandDao brandDao;


  @Autowired
  private ProductQueryService productQueryService;

  @Test
  @DisplayName("입력받은 카테고리의 최저가격 브랜드와 최고가격 브랜드를 반환한다")
  void searchMinAndMaxProductsByCategory() {
    // given
    String categoryName = "상의";
    CategoryId categoryId = CategoryId.of(0L);
    CategoryData category = new CategoryData(categoryId, CategoryType.TOP);
    BrandData brandC = new BrandData(BrandId.of(0L), "C");
    BrandData brandI = new BrandData(BrandId.of(1L), "I");

    ProductData minProduct1 = new ProductData(ProductId.of(0L), categoryId, brandC.getId(), Money.of(BigDecimal.valueOf(10000L)));
    ProductData minProduct2 = new ProductData(ProductId.of(1L), categoryId, brandI.getId(), Money.of(BigDecimal.valueOf(10000L)));

    ProductData maxProduct = new ProductData(ProductId.of(2L), categoryId, brandI.getId(), Money.of(BigDecimal.valueOf(11400L)));

    // when
    when(categoryDao.findByType(CategoryType.findByName(categoryName)))
        .thenReturn(Optional.of(category));

    when(productDao.findTop1ByCategoryIdOrderByPriceAsc(categoryId))
        .thenReturn(Optional.of(minProduct1));
    when(productDao.findTop1ByCategoryIdOrderByPriceDesc(categoryId))
        .thenReturn(Optional.of(maxProduct));

    when(productDao.findByPrice(Money.of(BigDecimal.valueOf(10000L))))
        .thenReturn(List.of(minProduct1, minProduct2));
    when(productDao.findByPrice(Money.of(BigDecimal.valueOf(11400L))))
        .thenReturn(List.of(maxProduct));

    when(brandDao.findById(brandC.getId()))
        .thenReturn(Optional.of(brandC));
    when(brandDao.findById(brandI.getId()))
        .thenReturn(Optional.of(brandI));

    var result = productQueryService.searchMinAndMaxProductsByCategory(categoryName);

    // then
    assertThat(result.categoryName()).isEqualTo("상의");
    
    assertThat(result.minPrice()).hasSize(2)
        .extracting("brandName", "price")
        .containsExactlyInAnyOrder(
            tuple(brandC.getName(), minProduct1.getPrice().getValue()),
            tuple(brandI.getName(), minProduct1.getPrice().getValue())
        );

    assertThat(result.maxPrice()).hasSize(1)
        .extracting("brandName", "price")
        .containsExactlyInAnyOrder(
            tuple(brandI.getName(), maxProduct.getPrice().getValue())
        );
  }
}