package musinsa_assignment.style_coordinator.catalog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Brand {

  @EmbeddedId
  private BrandId id;

  @Column(nullable = false, unique = true)
  private String name;

  @Builder
  public Brand(BrandId id, String name) {
    this.id = id;
    this.name = name;
  }

  public void updateName(String name) {
    this.name = name;
  }
}
