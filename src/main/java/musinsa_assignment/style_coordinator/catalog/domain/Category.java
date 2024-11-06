package musinsa_assignment.style_coordinator.catalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
  @EmbeddedId
  private CategoryId id;

  @Enumerated(EnumType.STRING)
  @Column(unique = true)
  private CategoryType type;

}
