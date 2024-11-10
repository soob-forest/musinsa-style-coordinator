package musinsa_assignment.style_coordinator.catalog.query.dao;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.query.dto.ProductData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductDataDaoTest {

  @Autowired
  private ProductDao productDao;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.update("truncate table category");
    jdbcTemplate.update("truncate table brand");
    jdbcTemplate.update("truncate table product");
  }

  @Test
  @DisplayName("특정 카테고리에서 가장 가격이 높은 제품을 조회한다")
  void findCheapestByCategoryId() {
    // given
    jdbcTemplate.update("insert into category values(?,?)", 0, "TOP");
    jdbcTemplate.update("insert into brand values(?,?)", 0, "A");

    String cheapestProductId1 = "0";
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", cheapestProductId1, 0, 0,
        BigDecimal.valueOf(10000));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", 1, 0, 1, BigDecimal.valueOf(10500));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", 2, 0, 2, BigDecimal.valueOf(11200));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", 3, 0, 3, BigDecimal.valueOf(10100));

    String cheapestProductId2 = "5";
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", 4, 1, 1, BigDecimal.valueOf(5900));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", cheapestProductId2, 1, 0,
        BigDecimal.valueOf(5500));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", 6, 1, 2, BigDecimal.valueOf(6200));

    // when
    ProductData productData1 = productDao.findTop1ByCategoryIdOrderByPriceAsc(CategoryId.of("0")).get();
    ProductData productData2 = productDao.findTop1ByCategoryIdOrderByPriceAsc(CategoryId.of("1")).get();

    // then
    assertThat(productData1.getId()).isEqualTo(ProductId.of(cheapestProductId1));
    assertThat(productData2.getId()).isEqualTo(ProductId.of(cheapestProductId2));
  }
}