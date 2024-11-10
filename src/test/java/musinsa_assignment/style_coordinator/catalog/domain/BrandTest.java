package musinsa_assignment.style_coordinator.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BrandTest {

  @Test
  void updateName() {
    // given
    var brand = Brand.builder().id(BrandId.of("id")).name("A").build();

    // when
    String updatedName = "B";
    brand.updateName(updatedName);

    // then
    assertThat(brand.getName()).isEqualTo("B");
  }
}