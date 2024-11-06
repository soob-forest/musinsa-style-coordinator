package musinsa_assignment.style_coordinator.catalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand")
public class Brand {
  @EmbeddedId
  private BrandId id;

  @Column(nullable = false, unique = true)
  private String name;

}
