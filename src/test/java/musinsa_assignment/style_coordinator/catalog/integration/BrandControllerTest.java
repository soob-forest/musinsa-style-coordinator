package musinsa_assignment.style_coordinator.catalog.integration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.restassured.RestAssured;
import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.controller.BrandController;
import musinsa_assignment.style_coordinator.catalog.query.application.brand.CheapestBrandView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BrandControllerTest {

  @LocalServerPort
  int port;
  @Autowired
  private BrandController brandController;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  @DisplayName("고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 한다.")
  void getCheapestBrand() {

    // when
    var response = given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/api/v1/brands/cheapest")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .extract().as(CheapestBrandView.class);

    // then
    assertThat(response.cheapestPrice())
        .extracting("brandName", "totalPrice")
        .containsExactly("D", new BigDecimal("36100.00"));

    assertThat(response.cheapestPrice().cheapestCategoryViewsViews())
        .hasSize(8)
        .extracting("categoryName", "price")
        .containsExactly(
            tuple("상의", new BigDecimal("10100.00")),
            tuple("아우터", new BigDecimal("5100.00")),
            tuple("바지", new BigDecimal("3000.00")),
            tuple("스니커즈", new BigDecimal("9500.00")),
            tuple("가방", new BigDecimal("2500.00")),
            tuple("모자", new BigDecimal("1500.00")),
            tuple("양말", new BigDecimal("2400.00")),
            tuple("악세서리", new BigDecimal("2000.00"))
        );
  }
}
