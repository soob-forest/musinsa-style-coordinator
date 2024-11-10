package musinsa_assignment.style_coordinator.catalog.integration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.restassured.RestAssured;
import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.query.application.product.MinAndMaxPriceProductView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:init-test.sql")
public class ProductControllerTest {

  @LocalServerPort
  int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  @DisplayName("고객은 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회할 수 있어야 한다.")
  void minAndMaxPriceProductByCategory() {

    // when
    var response = given()
        .queryParam("category", "상의")
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/api/v1/products/search/minAndMaxPriceProduct")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .extract().as(MinAndMaxPriceProductView.class);

    // then
    assertThat(response.categoryName()).isEqualTo("상의");

    assertThat(response.minPrice()).hasSize(1)
        .extracting("brandName", "price")
        .containsExactlyInAnyOrder(
            tuple("C", new BigDecimal("10000.00"))
        );

    assertThat(response.maxPrice()).hasSize(1)
        .extracting("brandName", "price")
        .containsExactlyInAnyOrder(
            tuple("I", new BigDecimal("11400.00"))
        );
  }
}
