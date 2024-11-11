package musinsa_assignment.style_coordinator.catalog.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import musinsa_assignment.style_coordinator.catalog.ui.controller.BrandController;
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

    // when, then
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/api/v1/brands/cheapest")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("data.cheapestPrice.brandName", equalTo("D"))
        .body("data.cheapestPrice.totalPrice", equalTo(36100.00f))
        .body("data.cheapestPrice.cheapestCategoryViewsViews.size()", equalTo(8))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[0].categoryName", equalTo("상의"))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[0].price", equalTo(10100.00f))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[1].categoryName", equalTo("아우터"))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[1].price", equalTo(5100.00f))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[2].categoryName", equalTo("바지"))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[2].price", equalTo(3000.00f))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[3].categoryName", equalTo("스니커즈"))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[3].price", equalTo(9500.00f))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[4].categoryName", equalTo("가방"))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[4].price", equalTo(2500.00f))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[5].categoryName", equalTo("모자"))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[5].price", equalTo(1500.00f))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[6].categoryName", equalTo("양말"))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[6].price", equalTo(2400.00f))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[7].categoryName", equalTo("악세서리"))
        .body("data.cheapestPrice.cheapestCategoryViewsViews[7].price", equalTo(2000.00f));


  }
}
