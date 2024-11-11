package musinsa_assignment.style_coordinator.ranking.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.common.domain.Money;

@Entity
@Table(name = "min_price_ranking")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MinPriceRanking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Embedded
  private CategoryId categoryId;
  @Embedded
  private BrandId brandId;
  @Embedded
  private ProductId productId;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "price"))
  private Money price;

  @Builder
  public MinPriceRanking(CategoryId categoryId, BrandId brandId, ProductId productId, Money price) {
    this.categoryId = categoryId;
    this.brandId = brandId;
    this.productId = productId;
    this.price = price;
  }
}
