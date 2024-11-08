package musinsa_assignment.style_coordinator.catalog.query.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;

@Entity
@Table(name = "brand")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BrandData {

  @EmbeddedId
  private BrandId id;

  @Column(nullable = false, unique = true)
  private String name;

  @Builder
  public BrandData(BrandId id, String name) {
    this.id = id;
    this.name = name;
  }
}
