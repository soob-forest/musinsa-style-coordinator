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
import java.util.List;
import java.util.Optional;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRankingData;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRankingService;
import musinsa_assignment.style_coordinator.catalog.query.application.category.CategoryQueryService;
import musinsa_assignment.style_coordinator.catalog.query.dao.BrandDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.CategoryDao;
import musinsa_assignment.style_coordinator.catalog.query.dao.ProductDao;
import musinsa_assignment.style_coordinator.catalog.query.dto.BrandData;
import musinsa_assignment.style_coordinator.catalog.query.dto.CategoryData;
import musinsa_assignment.style_coordinator.catalog.query.dto.ProductData;
import musinsa_assignment.style_coordinator.common.domain.Money;
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
  @MockBean
  private ProductRankingService productRankingService;

  @Autowired
  private CategoryQueryService categoryQueryService;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    // given, when
    when(categoryDao.findAll()).thenReturn(Arrays.asList(
        CategoryData.builder().id(CategoryId.of("0")).type(TOP).build(),
        CategoryData.builder().id(CategoryId.of("1")).type(OUTER).build(),
        CategoryData.builder().id(CategoryId.of("2")).type(PANTS).build()
    ));

    when(productRankingService.findMinPriceRankingByCategoryId(any(CategoryId.class)))
        .thenAnswer((Answer<List<ProductRankingData>>) invocation -> {
          CategoryId categoryId = invocation.getArgument(0);
          if (categoryId.equals(CategoryId.of("0"))) {
            return List.of(new ProductRankingData(
                CategoryId.of("0"),
                BrandId.of("2"),
                ProductId.of("0"),
                Money.of(BigDecimal.valueOf(10000L))));
          }
          if (categoryId.equals(CategoryId.of("1"))) {
            return List.of(new ProductRankingData(CategoryId.of("1"), BrandId.of("5"), ProductId.of("1"), Money.of(BigDecimal.valueOf(5000L))));
          }
          return List.of(new ProductRankingData(CategoryId.of("2"), BrandId.of("4"), ProductId.of("2"), Money.of(BigDecimal.valueOf(9000L))));
        });

    when(productRankingService.findMinPriceRankingByBrandId(any(BrandId.class)))
        .thenReturn(List.of());

    when(productDao.findById(any(ProductId.class)))
        .thenAnswer((Answer<Optional<ProductData>>) invocation -> {
          ProductId productId = invocation.getArgument(0);
          if (productId.equals(ProductId.of("0"))) {
            return Optional.of(ProductData.builder()
                .id(ProductId.of("0"))
                .price(Money.of(BigDecimal.valueOf(10000L)))
                .build());
          }
          if (productId.equals(ProductId.of("1"))) {
            return Optional.of(ProductData.builder()
                .id(ProductId.of("1"))
                .price(Money.of(BigDecimal.valueOf(5000L)))
                .build());
          }
          return Optional.of(ProductData.builder()
              .id(ProductId.of("2"))
              .price(Money.of(BigDecimal.valueOf(9000L)))
              .build());
        });

    doAnswer((Answer<Optional<BrandData>>) invocation -> {
      BrandId brandId = invocation.getArgument(0);

      if (brandId.equals(BrandId.of("2"))) {
        return Optional.of(BrandData.builder()
            .id(BrandId.of("2"))
            .name("C")
            .build());
      }
      if (brandId.equals(BrandId.of("5"))) {
        return Optional.of(BrandData.builder()
            .id(BrandId.of("5"))
            .name("E")
            .build());
      }
      return Optional.of(BrandData.builder()
          .id(BrandId.of("4"))
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