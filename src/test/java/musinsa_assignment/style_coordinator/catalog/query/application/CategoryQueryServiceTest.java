package musinsa_assignment.style_coordinator.catalog.query.application;

import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.OUTER;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.PANTS;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.TOP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.Money;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.query.application.category.CategoryQueryService;
import musinsa_assignment.style_coordinator.catalog.query.dao.BrandDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.CategoryDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.ProductDao;
import musinsa_assignment.style_coordinator.catalog.query.dto.BrandData;
import musinsa_assignment.style_coordinator.catalog.query.dto.CategoryData;
import musinsa_assignment.style_coordinator.catalog.query.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class CategoryQueryServiceTest {

  @MockBean
  private CategoryDao categoryDao;
  @MockBean
  private ProductDao productDao;
  @MockBean
  private BrandDao brandDao;


  @Autowired
  private CategoryQueryService categoryQueryService;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    // given, when
    when(categoryDao.findAll()).thenReturn(Arrays.asList(
        CategoryData.builder().id(CategoryId.of(0L)).type(TOP).build(),
        CategoryData.builder().id(CategoryId.of(1L)).type(OUTER).build(),
        CategoryData.builder().id(CategoryId.of(2L)).type(PANTS).build()
    ));

    doAnswer((Answer<Optional<ProductData>>) invocation -> {
      CategoryId categoryId = invocation.getArgument(0);

      if (categoryId.equals(CategoryId.of(0L))) {
        return Optional.of(ProductData.builder()
            .id(ProductId.of(0L))
            .categoryId(CategoryId.of(0L))
            .brandId(BrandId.of(2L))
            .price(Money.of(BigDecimal.valueOf(10000L)))
            .build());
      }
      if (categoryId.equals(CategoryId.of(1L))) {
        return Optional.of(ProductData.builder()
            .id(ProductId.of(1L))
            .categoryId(CategoryId.of(1L))
            .brandId(BrandId.of(5L))
            .price(Money.of(BigDecimal.valueOf(5000L)))
            .build());
      }
      return Optional.of(ProductData.builder()
          .id(ProductId.of(2L))
          .categoryId(CategoryId.of(2L))
          .brandId(BrandId.of(4L))
          .price(Money.of(BigDecimal.valueOf(9000L)))
          .build());

    }).when(productDao).findTop1ByCategoryIdOrderByPriceAsc(any(CategoryId.class));

    doAnswer((Answer<Optional<BrandData>>) invocation -> {
      BrandId brandId = invocation.getArgument(0);

      if (brandId.equals(BrandId.of(2L))) {
        return Optional.of(BrandData.builder()
            .id(BrandId.of(2L))
            .name("C")
            .build());
      }
      if (brandId.equals(BrandId.of(5L))) {
        return Optional.of(BrandData.builder()
            .id(BrandId.of(5L))
            .name("E")
            .build());
      }
      return Optional.of(BrandData.builder()
          .id(BrandId.of(4L))
          .name("D")
          .build());

    }).when(brandDao).findById(any(BrandId.class));
  }

  @Test
  @DisplayName("카테고리별 가장 싼 브랜드의 이름들을 카테고리별로 묶어서 가격과 함께 노출 순서에 따라 반환한다.")
  void findCheapestProductBrandNameByCategory() {
    // when
    var result = categoryQueryService.getCheapestPerCategoryView();
    // then
    assertThat(result.contents()).hasSize(3)
        .extracting("categoryName", "brandName", "price")
        .containsExactly(
            tuple(TOP.getName(), "C", BigDecimal.valueOf(10000L)),
            tuple(OUTER.getName(), "E", BigDecimal.valueOf(5000L)),
            tuple(PANTS.getName(), "D", BigDecimal.valueOf(9000L))
        );


  }

  @Test
  @DisplayName("제품 가격을 합친 총액을 반환한다.")
  void findTotalPrice() {
    // when
    var result = categoryQueryService.getCheapestPerCategoryView();
    // then
    assertThat(result.totalPrice()).isEqualTo(BigDecimal.valueOf(24000L));
  }
}