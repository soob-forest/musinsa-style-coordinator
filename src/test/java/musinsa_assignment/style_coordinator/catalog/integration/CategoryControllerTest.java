package musinsa_assignment.style_coordinator.catalog.integration;

import static io.restassured.RestAssured.given;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.ACCESSORY;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.BAG;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.HAT;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.OUTER;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.PANTS;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.SNEAKERS;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.SOCKS;
import static musinsa_assignment.style_coordinator.catalog.domain.CategoryType.TOP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import io.restassured.RestAssured;
import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.controller.CategoryController;
import musinsa_assignment.style_coordinator.catalog.query.application.category.CheapestPerCategoryView;
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
public class CategoryControllerTest {

  @LocalServerPort
  int port;
  @Autowired
  private CategoryController categoryController;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @Test
  @DisplayName("고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 한다.")
  void getCheapestPerCategoryView() {
    // when
    var response = given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/api/v1/categories/cheapestProducts")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .extract().as(CheapestPerCategoryView.class);

    // then
    assertThat(response.contents()).hasSize(8)
        .extracting("categoryName", "brandName", "price")
        .containsExactly(
            tuple(TOP.getName(), "C", new BigDecimal("10000.00")),
            tuple(OUTER.getName(), "E", new BigDecimal("5000.00")),
            tuple(PANTS.getName(), "D", new BigDecimal("3000.00")),
            // TODO 현재 구현상으로는 A와 G가 가격이 같아, A가 노출되어 수정 필요
            tuple(SNEAKERS.getName(), "A", new BigDecimal("9000.00")),
            tuple(BAG.getName(), "A", new BigDecimal("2000.00")),
            tuple(HAT.getName(), "D", new BigDecimal("1500.00")),
            tuple(SOCKS.getName(), "I", new BigDecimal("1700.00")),
            tuple(ACCESSORY.getName(), "F", new BigDecimal("1900.00"))
        );

  }
}
