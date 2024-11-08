package musinsa_assignment.style_coordinator.catalog.query.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryType;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
public class CategoryData {

  @EmbeddedId
  private CategoryId id;

  @Enumerated(EnumType.STRING)
  @Column(unique = true)
  private CategoryType type;

  @Builder
  public CategoryData(CategoryId id, CategoryType type) {
    this.id = id;
    this.type = type;
  }
}
