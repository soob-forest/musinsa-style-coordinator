package musinsa_assignment.style_coordinator.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

  @Test
  @DisplayName("자신과 같은 값을 가지고 있는 updateModelBuilder 반환해야 한다.")
  void toUpdateModelBuilder() {
    // given
    var product = Product.builder()
        .id(ProductId.of("0"))
        .categoryId(CategoryId.of("0"))
        .brandId(BrandId.of("0"))
        .price(Money.of(new BigDecimal("1000.00")))
        .build();

    // when
    var modelBuilder = product.toUpdateModelBuilder();

    // when
    assertThat(modelBuilder.build().getCategoryId()).isEqualTo(product.getCategoryId());
    assertThat(modelBuilder.build().getBrandId()).isEqualTo(product.getBrandId());
    assertThat(modelBuilder.build().getPrice()).isEqualTo(product.getPrice());
  }

  @Test
  void productUpdate() {
    // given
    var product = Product.builder()
        .id(ProductId.of("0"))
        .categoryId(CategoryId.of("0"))
        .brandId(BrandId.of("0"))
        .price(Money.of(new BigDecimal("1000.00")))
        .build();
    var modelBuilder = product.toUpdateModelBuilder();

    // when
    var updatedCategoryId = "1";
    var updatedBrandId = "1";
    var updatedPrice = new BigDecimal("2000.00");

    product.update(modelBuilder.categoryId(updatedCategoryId).brandId(updatedBrandId).price(updatedPrice).build());

    // then
    assertThat(product.getCategoryId()).isEqualTo(CategoryId.of(updatedCategoryId));
    assertThat(product.getBrandId()).isEqualTo(BrandId.of(updatedBrandId));
    assertThat(product.getPrice()).isEqualTo(Money.of(updatedPrice));
  }
}
