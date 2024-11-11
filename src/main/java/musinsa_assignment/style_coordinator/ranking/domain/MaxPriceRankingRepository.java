package musinsa_assignment.style_coordinator.ranking.domain;

import java.util.List;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import org.springframework.data.repository.Repository;

public interface MaxPriceRankingRepository extends Repository<MaxPriceRanking, Long> {

  List<MaxPriceRanking> findByCategoryId(CategoryId categoryId);

  void save(MaxPriceRanking maxPriceRanking);

  void deleteAllInBatch();
}
