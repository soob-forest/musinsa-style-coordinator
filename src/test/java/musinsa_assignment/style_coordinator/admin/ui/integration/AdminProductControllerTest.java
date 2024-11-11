package musinsa_assignment.style_coordinator.admin.ui.integration;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.command.application.product.ProductRequest;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.Money;
import musinsa_assignment.style_coordinator.catalog.domain.Product;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRepository;
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
class AdminProductControllerTest {

  @LocalServerPort
  int port;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private ProductRepository productRepository;


  @BeforeEach
  void setUp() {
    RestAssured.port = port;

    jdbcTemplate.update("insert into category values (0, 'TOP')");
    jdbcTemplate.update("insert into category values (1, 'OUTER')");
    jdbcTemplate.update("insert into category values (2, 'PANTS')");
    jdbcTemplate.update("insert into category values (3, 'SNEAKERS')");
    jdbcTemplate.update("insert into category values (4, 'BAG')");
    jdbcTemplate.update("insert into category values (5, 'HAT')");
    jdbcTemplate.update("insert into category values (6, 'SOCKS')");
    jdbcTemplate.update("insert into category values (7, 'ACCESSORY')");

    jdbcTemplate.update("insert into brand values (0, 'A')");
    jdbcTemplate.update("insert into brand values (1, 'B')");
    jdbcTemplate.update("insert into brand values (2, 'C')");
    jdbcTemplate.update("insert into brand values (3, 'D')");
    jdbcTemplate.update("insert into brand values (4, 'E')");
    jdbcTemplate.update("insert into brand values (5, 'F')");
    jdbcTemplate.update("insert into brand values (6, 'G')");
    jdbcTemplate.update("insert into brand values (7, 'H')");
  }

  @AfterEach
  void tearDown() {
    jdbcTemplate.update("truncate table product");
    jdbcTemplate.update("truncate table category");
    jdbcTemplate.update("truncate table brand");
  }

  @Test
  @DisplayName("admin사용자는 제품을 생성할 수 있다.")
  void create() {
    // given
    var request = new ProductRequest("0", "0", new BigDecimal("1000.00"));

    // when
    given()
        .body(request)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .post("/admin/api/v1/products")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value())
        .body("data.id", notNullValue())
        .body("data.categoryId", equalTo(request.categoryId()))
        .body("data.brandId", equalTo(request.brandId()))
        .body("data.price", equalTo(1000.00f));

  }

  @Test
  @DisplayName("admin사용자는 제품 가격만 변경할 수 있다.")
  void updatePrice() {
    // given
    var product = Product.builder()
        .id(ProductId.of("0"))
        .categoryId(CategoryId.of("0"))
        .brandId(BrandId.of("0"))
        .price(Money.of(new BigDecimal("1000.00")))
        .build();
    productRepository.save(product);

    // when 가격을 변경하면
    var request = new ProductRequest(null, null, new BigDecimal("2000.00"));
    given()
        .pathParam("id", product.getId().getValue())
        .body(request)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .patch("/admin/api/v1/products/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value());

    // then 가격만 변경된다
    var updatedProduct = productRepository.findById(product.getId()).get();

    assertThat(updatedProduct.getPrice().getValue()).isEqualTo(request.price());
    assertThat(updatedProduct.getCategoryId()).isEqualTo(product.getCategoryId());
    assertThat(updatedProduct.getBrandId()).isEqualTo(product.getBrandId());
  }

  @Test
  @DisplayName("admin사용자는 제품 브랜드만 변경할 수 있다.")
  void updateBrand() {
    // given
    var product = Product.builder()
        .id(ProductId.of("0"))
        .categoryId(CategoryId.of("0"))
        .brandId(BrandId.of("0"))
        .price(Money.of(new BigDecimal("1000.00")))
        .build();
    productRepository.save(product);

    // when 브랜드를 변경하면
    var request = new ProductRequest(null, "1", null);
    given()
        .pathParam("id", product.getId().getValue())
        .body(request)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .patch("/admin/api/v1/products/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value());

    // then 브랜드만 변경된다
    var updatedProduct = productRepository.findById(product.getId()).get();

    assertThat(updatedProduct.getPrice()).isEqualTo(product.getPrice());
    assertThat(updatedProduct.getCategoryId()).isEqualTo(product.getCategoryId());
    assertThat(request.brandId()).isEqualTo(updatedProduct.getBrandId().getValue());
  }

  @Test
  @DisplayName("admin사용자는 제품 카테고리만 변경할 수 있다.")
  void updateCategory() {
    // given
    var product = Product.builder()
        .id(ProductId.of("0"))
        .categoryId(CategoryId.of("0"))
        .brandId(BrandId.of("0"))
        .price(Money.of(new BigDecimal("1000.00")))
        .build();
    productRepository.save(product);

    // when 카테고리을 변경하면
    var request = new ProductRequest("1", null, null);
    given()
        .pathParam("id", product.getId().getValue())
        .body(request)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .patch("/admin/api/v1/products/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value());

    // then 카테고리만 변경된다
    var updatedProduct = productRepository.findById(product.getId()).get();

    assertThat(updatedProduct.getPrice()).isEqualTo(product.getPrice());
    assertThat(request.categoryId()).isEqualTo(updatedProduct.getCategoryId().getValue());
    assertThat(updatedProduct.getBrandId()).isEqualTo(product.getBrandId());
  }

  @Test
  @DisplayName("admin사용자는 제품의 여러 정보를 동시에 변경할 수 있다.")
  void update() {
    // given
    var product = Product.builder()
        .id(ProductId.of("0"))
        .categoryId(CategoryId.of("0"))
        .brandId(BrandId.of("0"))
        .price(Money.of(new BigDecimal("1000.00")))
        .build();
    productRepository.save(product);

    // when
    var request = new ProductRequest("1", "1", new BigDecimal("2000.00"));
    given()
        .pathParam("id", product.getId().getValue())
        .body(request)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .patch("/admin/api/v1/products/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value());

    // then
    var updatedProduct = productRepository.findById(product.getId()).get();

    assertThat(request.price()).isEqualTo(updatedProduct.getPrice().getValue());
    assertThat(request.categoryId()).isEqualTo(updatedProduct.getCategoryId().getValue());
    assertThat(request.brandId()).isEqualTo(updatedProduct.getBrandId().getValue());
  }

  @Test
  @DisplayName("admin사용자는 제품 정보를 삭제할 수 있다.")
  void delete() {
    // given
    var product = Product.builder()
        .id(ProductId.of("0"))
        .categoryId(CategoryId.of("0"))
        .brandId(BrandId.of("0"))
        .price(Money.of(new BigDecimal("1000.00")))
        .build();
    productRepository.save(product);

    // when
    String newBrandName = "B";
    given()
        .pathParam("id", product.getId().getValue())
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
        .delete("/admin/api/v1/products/{id}")
        .then()
        .log().all()
        .statusCode(HttpStatus.OK.value());

    // then
    var optionalProduct = productRepository.findById(product.getId());
    assertThat(optionalProduct.isEmpty()).isEqualTo(true);
  }
}