package musinsa_assignment.style_coordinator.catalog.query.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.query.dto.BrandData;
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
class BrandDaoTest {

  @Autowired
  private BrandDao brandDao;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.update("truncate table category");
    jdbcTemplate.update("truncate table brand");
    jdbcTemplate.update("truncate table product");
  }

  @Test
  @DisplayName("모든 카테고리 제품의 가격의 합이 가장 작은 브랜드를 반환")
  void findCheapest() {
    // given
    jdbcTemplate.update("insert into category values(?,?)", "0", "TOP");
    jdbcTemplate.update("insert into category values(?,?)", "1", "OUTER");
    jdbcTemplate.update("insert into category values(?,?)", "2", "PANTS");

    jdbcTemplate.update("insert into brand values(?,?)", "0", "A");
    jdbcTemplate.update("insert into brand values(?,?)", "1", "B");
    jdbcTemplate.update("insert into brand values(?,?)", "2", "C");

    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "0", "0", "0",
        BigDecimal.valueOf(10000));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "1", "1", "0", BigDecimal.valueOf(10000));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "2", "2", "0", BigDecimal.valueOf(10000));

    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "3", "0", "1", BigDecimal.valueOf(20000));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "4", "1", "1", BigDecimal.valueOf(20000));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "5", "2", "1",
        BigDecimal.valueOf(20000));

    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "6", "0", "2", BigDecimal.valueOf(30000));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "7", "1", "2", BigDecimal.valueOf(30000));
    jdbcTemplate.update("insert into product(product_id, category_id, brand_id, price) values(?,?,?,?)", "8", "2", "2", BigDecimal.valueOf(30000));

    // when
    BrandData result = brandDao.findCheapest();

    // then
    assertThat(result.getId()).isEqualTo(BrandId.of("0"));
  }
}