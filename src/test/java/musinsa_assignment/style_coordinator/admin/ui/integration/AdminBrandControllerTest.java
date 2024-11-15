package musinsa_assignment.style_coordinator.admin.ui.integration;

import static io.restassured.RestAssured.given;
import static musinsa_assignment.style_coordinator.common.exception.ApplicationErrorCode.NO_BRAND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import java.util.Map;
import musinsa_assignment.style_coordinator.catalog.domain.Brand;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.BrandRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AdminBrandControllerTest {

  @LocalServerPort
  int port;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private BrandRepository brandRepository;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @AfterEach
  void tearDown() {
    jdbcTemplate.update("truncate table brand");
  }

  @Test
  @DisplayName("admin사용자는 브랜드를 생성할 수 있다.")
  void create() {
    // given
    var newBrandName = "new";
    // when
    given()
        .body(Map.of("name", newBrandName))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post("/admin/api/v1/brands")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("data.id", notNullValue())
        .body("data.name", equalTo(newBrandName));
  }

  @Test
  @DisplayName("admin사용자는 브랜드 정보를 변경할 수 있다.")
  void update() {
    // given
    var brand = Brand.builder().id(BrandId.of("0")).name("A").build();
    brandRepository.save(brand);
    // when
    String newBrandName = "B";
    given()
        .pathParam("id", brand.getId().getValue())
        .body(Map.of("name", newBrandName))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .patch("/admin/api/v1/brands/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value());

    // then
    var updatedBrand = brandRepository.findById(brand.getId()).get();
    assertThat(updatedBrand.getName()).isEqualTo(newBrandName);
  }

  @Test
  @DisplayName("존재하지 않는 브랜드의 정보는 변경할 수 없다.")
  void updateFailed() {
    // when
    String newBrandName = "B";
    given()
        .pathParam("id", "noBrandId")
        .body(Map.of("name", newBrandName))
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .patch("/admin/api/v1/brands/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .body("data.code", equalTo(NO_BRAND.getCode()))
        .body("data.message", equalTo(NO_BRAND.getMessage()));
  }

  @Test
  @DisplayName("admin사용자는 브랜드 정보를 삭제할 수 있다.")
  void delete() {
    // given
    var brand = Brand.builder().id(BrandId.of("0")).name("A").build();
    brandRepository.save(brand);

    // when
    given()
        .pathParam("id", brand.getId().getValue())
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .delete("/admin/api/v1/brands/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value());

    // then
    var optionalBrand = brandRepository.findById(brand.getId());
    assertThat(optionalBrand.isEmpty()).isEqualTo(true);
  }

  @Test
  @DisplayName("admin사용자는 존재하지 않는 브랜드 정보는 삭제할 수 없다.")
  void deleteFailed() {
    // given
    var brand = Brand.builder().id(BrandId.of("0")).name("A").build();
    brandRepository.save(brand);

    // when
    given()
        .pathParam("id", "noBrandId")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .delete("/admin/api/v1/brands/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .body("data.code", equalTo(NO_BRAND.getCode()))
        .body("data.message", equalTo(NO_BRAND.getMessage()));
  }
}