package musinsa_assignment.style_coordinator.catalog.domain;

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
import musinsa_assignment.style_coordinator.catalog.domain.ProductUpdateModel.ProductUpdateModelBuilder;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

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
  private Product(ProductId id, CategoryId categoryId, BrandId brandId, Money price) {
    this.id = id;
    this.categoryId = categoryId;
    this.brandId = brandId;
    this.price = price;
  }

  public ProductUpdateModelBuilder toUpdateModelBuilder() {
    return ProductUpdateModel.builder()
        .categoryId(categoryId.getValue()).brandId(brandId.getValue()).price(price.getValue());
  }

  public void update(ProductUpdateModel updateModel) {
    this.categoryId = updateModel.getCategoryId();
    this.brandId = updateModel.getBrandId();
    this.price = updateModel.getPrice();
  }
}
