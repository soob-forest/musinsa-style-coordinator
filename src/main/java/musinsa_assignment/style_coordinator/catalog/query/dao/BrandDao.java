package musinsa_assignment.style_coordinator.catalog.query.dao;

import java.util.Optional;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.query.dto.BrandData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface BrandDao extends Repository<BrandData, BrandId> {

  Optional<BrandData> findById(BrandId id);

  // brand별로 상품을 묶었을 경우 가격이 가장 작은 brand 반환
  @Query("""
       SELECT b FROM BrandData b
            WHERE b.id = (
                SELECT p.brandId
                FROM ProductData p
                GROUP BY p.brandId
                HAVING SUM(p.price.value) = (
                    SELECT MIN(total_price)
                    FROM (
                        SELECT SUM(p2.price.value) as total_price
                        FROM ProductData p2
                        GROUP BY p2.brandId
                    )
                )
            )
      """)
  BrandData findCheapest();
}
