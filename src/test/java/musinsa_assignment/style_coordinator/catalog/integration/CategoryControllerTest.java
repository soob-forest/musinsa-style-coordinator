package musinsa_assignment.style_coordinator.catalog.integration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import musinsa_assignment.style_coordinator.catalog.ui.controller.CategoryController;
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
    // when, then
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .get("/api/v1/categories/cheapestProducts")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("data.totalPrice", equalTo(34100.00f))
        .body("data.contents.size()", equalTo(8))
        // 상의
        .body("data.contents[0].categoryName", equalTo("상의"))
        .body("data.contents[0].brandName", equalTo("C"))
        .body("data.contents[0].price", equalTo(10000.00f))
        // 아우터
        .body("data.contents[1].categoryName", equalTo("아우터"))
        .body("data.contents[1].brandName", equalTo("E"))
        .body("data.contents[1].price", equalTo(5000.00f))
        // 바지
        .body("data.contents[2].categoryName", equalTo("바지"))
        .body("data.contents[2].brandName", equalTo("D"))
        .body("data.contents[2].price", equalTo(3000.00f))
        // TODO 현재 구현상으로는 A와 G가 가격이 같아, A가 노출되어 수정 필요
        // 스니커즈
        .body("data.contents[3].categoryName", equalTo("스니커즈"))
        .body("data.contents[3].brandName", equalTo("A"))
        .body("data.contents[3].price", equalTo(9000.00f))
        // 가방
        .body("data.contents[4].categoryName", equalTo("가방"))
        .body("data.contents[4].brandName", equalTo("A"))
        .body("data.contents[4].price", equalTo(2000.00f))
        // 모자
        .body("data.contents[5].categoryName", equalTo("모자"))
        .body("data.contents[5].brandName", equalTo("D"))
        .body("data.contents[5].price", equalTo(1500.00f))
        // 양말
        .body("data.contents[6].categoryName", equalTo("양말"))
        .body("data.contents[6].brandName", equalTo("I"))
        .body("data.contents[6].price", equalTo(1700.00f))
        // 액세서리
        .body("data.contents[7].categoryName", equalTo("악세서리"))
        .body("data.contents[7].brandName", equalTo("F"))
        .body("data.contents[7].price", equalTo(1900.00f));

  }
}
