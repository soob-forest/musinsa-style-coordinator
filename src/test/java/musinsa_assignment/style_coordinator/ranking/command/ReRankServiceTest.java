package musinsa_assignment.style_coordinator.ranking.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;

import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.common.domain.Money;
import musinsa_assignment.style_coordinator.ranking.domain.MaxPriceRankingRepository;
import musinsa_assignment.style_coordinator.ranking.domain.MinPriceRankingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ReRankServiceTest {

  @Autowired
  private MinPriceRankingRepository minPriceRankingRepository;
  @Autowired
  private MaxPriceRankingRepository maxPriceRankingRepository;
  @Autowired
  private ReRankService reRankService;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.update("truncate table min_price_ranking");
    jdbcTemplate.update("truncate table max_price_ranking");
  }

  @Test
  @DisplayName("특정 카테고리에 최초로 랭킹 상품으로 등록 시, 최저, 최고 랭킹에 항상 등록 된다.")
  void reRankFirst() {  

    var categoryId = CategoryId.of("c0");
    var brandId = BrandId.of("b0");
    var productId = ProductId.of("p0");
    var price = Money.of(new BigDecimal("1000.00"));
    reRankService.reRank(categoryId, brandId, productId, price);

    // then
    var result = minPriceRankingRepository.findByCategoryId(categoryId);
    assertThat(result).hasSize(1).extracting("categoryId", "brandId", "productId", "price")
        .containsExactlyInAnyOrder(tuple(categoryId, brandId, productId, price));

  }

  @Test
  @DisplayName("특정 카테고리에 가장 가격이 낮은 상품이 등록 되면 최저 랭킹에 등록 된다.")
  void rankMinPriceProduct() {
    // given
    var categoryId = CategoryId.of("c0");
    var existingBrandId = BrandId.of("b0");
    var existingProductId = ProductId.of("p0");
    var existingPrice = Money.of(new BigDecimal("1000.00"));
    reRankService.reRank(categoryId, existingBrandId, existingProductId, existingPrice);

    // when
    var newBrandId = BrandId.of("b1");
    var newProductId = ProductId.of("p1");
    var lowerPrice = Money.of(new BigDecimal("500.00"));
    reRankService.reRank(categoryId, newBrandId, newProductId, lowerPrice);

    // then
    var result = minPriceRankingRepository.findByCategoryId(categoryId);
    assertThat(result).hasSize(1)
        .extracting("categoryId", "brandId", "productId", "price")
        .containsExactlyInAnyOrder(tuple(categoryId, newBrandId, newProductId, lowerPrice));
  }

  @Test
  @DisplayName("특정 카테고리에 가장 가격이 높은 상품이 등록 되면 최고 랭킹에 등록 된다.")
  void rankMaxPriceProduct() {
    // given
    var categoryId = CategoryId.of("c0");
    var existingBrandId = BrandId.of("b0");
    var existingProductId = ProductId.of("p0");
    var existingPrice = Money.of(new BigDecimal("1000.00"));
    reRankService.reRank(categoryId, existingBrandId, existingProductId, existingPrice);

    // when
    var newBrandId = BrandId.of("b1");
    var newProductId = ProductId.of("p1");
    var higherPrice = Money.of(new BigDecimal("2000.00"));
    reRankService.reRank(categoryId, newBrandId, newProductId, higherPrice);

    // then
    var result = maxPriceRankingRepository.findByCategoryId(categoryId);
    assertThat(result).hasSize(1)
        .extracting("categoryId", "brandId", "productId", "price")
        .containsExactlyInAnyOrder(tuple(categoryId, newBrandId, newProductId, higherPrice));
  }

  @Test
  @DisplayName("특정 카테고리에 가장 가격이 높지도, 낮지도 않은 상품이 등록 되면 변화가 없다.")
  void noRankProduct() {
    // given
    var categoryId = CategoryId.of("c0");
    var lowestBrandId = BrandId.of("b0");
    var lowestProductId = ProductId.of("p0");
    var lowestPrice = Money.of(new BigDecimal("500.00"));
    var highestBrandId = BrandId.of("b1");
    var highestProductId = ProductId.of("p1");
    var highestPrice = Money.of(new BigDecimal("2000.00"));

    reRankService.reRank(categoryId, lowestBrandId, lowestProductId, lowestPrice);
    reRankService.reRank(categoryId, highestBrandId, highestProductId, highestPrice);

    // when
    var newBrandId = BrandId.of("b2");
    var newProductId = ProductId.of("p2");
    var middlePrice = Money.of(new BigDecimal("1000.00"));
    reRankService.reRank(categoryId, newBrandId, newProductId, middlePrice);

    // then
    var minResult = minPriceRankingRepository.findByCategoryId(categoryId);
    assertThat(minResult).hasSize(1)
        .extracting("categoryId", "brandId", "productId", "price")
        .containsExactlyInAnyOrder(tuple(categoryId, lowestBrandId, lowestProductId, lowestPrice));

    var maxResult = maxPriceRankingRepository.findByCategoryId(categoryId);
    assertThat(maxResult).hasSize(1)
        .extracting("categoryId", "brandId", "productId", "price")
        .containsExactlyInAnyOrder(tuple(categoryId, highestBrandId, highestProductId, highestPrice));
  }

  @Test
  @DisplayName("특정 카테고리에 가장 낮은 가격이 중복되서 랭킹 될 수 있다.")
  void rankMinDuplicationProduct() {
    // given
    var categoryId = CategoryId.of("c0");
    var existingBrandId = BrandId.of("b0");
    var existingProductId = ProductId.of("p0");
    var lowestPrice = Money.of(new BigDecimal("500.00"));
    reRankService.reRank(categoryId, existingBrandId, existingProductId, lowestPrice);

    // when
    var newBrandId = BrandId.of("b1");
    var newProductId = ProductId.of("p1"); 
    reRankService.reRank(categoryId, newBrandId, newProductId, lowestPrice);

    // then
    var result = minPriceRankingRepository.findByCategoryId(categoryId);
    assertThat(result).hasSize(2)
        .extracting("categoryId", "brandId", "productId", "price")
        .containsExactlyInAnyOrder(
            tuple(categoryId, existingBrandId, existingProductId, lowestPrice),
            tuple(categoryId, newBrandId, newProductId, lowestPrice)
        );
  }

  @Test
  @DisplayName("특정 카테고리에 가장 높은 가격이 중복되서 랭킹 될 수 있다.")
  void rankMaxDuplicationProduct() {
    // given
    var categoryId = CategoryId.of("c0");
    var existingBrandId = BrandId.of("b0");
    var existingProductId = ProductId.of("p0");
    var highestPrice = Money.of(new BigDecimal("2000.00"));
    reRankService.reRank(categoryId, existingBrandId, existingProductId, highestPrice);  

    // when
    var newBrandId = BrandId.of("b1");
    var newProductId = ProductId.of("p1");
    reRankService.reRank(categoryId, newBrandId, newProductId, highestPrice); 

    // then
    var result = maxPriceRankingRepository.findByCategoryId(categoryId);
    assertThat(result).hasSize(2)
        .extracting("categoryId", "brandId", "productId", "price")
        .containsExactlyInAnyOrder(
            tuple(categoryId, existingBrandId, existingProductId, highestPrice),
            tuple(categoryId, newBrandId, newProductId, highestPrice)
        );  
  }
}