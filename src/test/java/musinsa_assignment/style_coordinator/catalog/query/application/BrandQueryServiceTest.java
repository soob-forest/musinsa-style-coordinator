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
import musinsa_assignment.style_coordinator.catalog.domain.CategoryType;
import musinsa_assignment.style_coordinator.catalog.domain.Money;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.query.application.brand.BrandQueryService;
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
public class BrandQueryServiceTest {

  @MockBean
  private CategoryDao categoryDao;
  @MockBean
  private ProductDao productDao;
  @MockBean
  private BrandDao brandDao;

  @Autowired
  private BrandQueryService brandQueryService;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
  }


  @Test
  @DisplayName("모든 카테고리의 가격 총 합이 가장 낮은 브랜드의 정보와 각 상품의 가격, 카테고리 리스트를 반환한다")
  void getCheapestCategoryBundleView() {
    // given, when
    when(brandDao.findCheapest()).thenReturn(BrandData.builder().id(BrandId.of("3L")).name("D").build());

    when(productDao.findByBrandId(any(BrandId.class))).thenReturn(Arrays.asList(
        ProductData.builder().id(ProductId.of("0L")).categoryId(CategoryId.of("0L")).brandId(BrandId.of("3L"))
            .price(Money.of(BigDecimal.valueOf(10000L)))
            .build(),
        ProductData.builder().id(ProductId.of("1L")).categoryId(CategoryId.of("1L")).brandId(BrandId.of("3L"))
            .price(Money.of(BigDecimal.valueOf(5100L)))
            .build(),
        ProductData.builder().id(ProductId.of("2L")).categoryId(CategoryId.of("2L")).brandId(BrandId.of("3L"))
            .price(Money.of(BigDecimal.valueOf(3000L)))
            .build()
    ));

    doAnswer((Answer<Optional<CategoryData>>) invocation -> {
      CategoryId categoryId = invocation.getArgument(0);

      if (categoryId.equals(CategoryId.of("0L"))) {
        return Optional.of(CategoryData.builder()
            .id(CategoryId.of("0L"))
            .type(TOP)
            .build());
      }
      if (categoryId.equals(CategoryId.of("1L"))) {
        return Optional.of(CategoryData.builder()
            .id(CategoryId.of("1L"))
            .type(CategoryType.OUTER)
            .build());
      }
      return Optional.of(CategoryData.builder()
          .id(CategoryId.of("2L"))
          .type(CategoryType.PANTS)
          .build());

    }).when(categoryDao).findById(any(CategoryId.class));

    // then
    var result = brandQueryService.getCheapestCategoryBundleView();

    assertThat(result.cheapestPrice().brandName()).isEqualTo("D");
    assertThat(result.cheapestPrice().cheapestCategoryViewsViews()).hasSize(3)
        .extracting("categoryName", "price")
        .containsExactlyInAnyOrder(
            tuple(TOP.getName(), BigDecimal.valueOf(10000L)),
            tuple(OUTER.getName(), BigDecimal.valueOf(5100L)),
            tuple(PANTS.getName(), BigDecimal.valueOf(3000L))
        );
    assertThat(result.cheapestPrice().totalPrice()).isEqualTo(BigDecimal.valueOf(18100L));
  }
}
