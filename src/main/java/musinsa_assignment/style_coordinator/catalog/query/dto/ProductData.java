package musinsa_assignment.style_coordinator.catalog.query.dto;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.Money;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductData {

  @EmbeddedId
  private ProductId id;
  @Embedded
  private CategoryId categoryId;
  @Embedded
  private BrandId brandId;
  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "price"))
  private Money price;

  @Builder
  public ProductData(ProductId id, CategoryId categoryId, BrandId brandId, Money price) {
    this.id = id;
    this.categoryId = categoryId;
    this.brandId = brandId;
    this.price = price;
  }
}

