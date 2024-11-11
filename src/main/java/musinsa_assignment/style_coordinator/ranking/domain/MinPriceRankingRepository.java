package musinsa_assignment.style_coordinator.ranking.domain;

import java.util.List;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import org.springframework.data.repository.Repository;

public interface MinPriceRankingRepository extends Repository<MinPriceRanking, Long> {

  List<MinPriceRanking> findByCategoryId(CategoryId categoryId);

  void save(MinPriceRanking minPriceRanking);

  void deleteAllInBatch();

  List<MinPriceRanking> findByBrandId(BrandId brandId);
}
