package musinsa_assignment.style_coordinator.catalog.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
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
}
