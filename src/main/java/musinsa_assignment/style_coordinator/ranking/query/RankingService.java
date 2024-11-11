package musinsa_assignment.style_coordinator.ranking.query;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.ranking.domain.MaxPriceRankingRepository;
import musinsa_assignment.style_coordinator.ranking.domain.MinPriceRankingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankingService {

  private final MinPriceRankingRepository minPriceRankingRepository;
  private final MaxPriceRankingRepository maxPriceRankingRepository;

  public List<PriceRankingData> findMinPriceRankingByCategoryId(CategoryId categoryId) {
    return minPriceRankingRepository.findByCategoryId(categoryId).stream()
        .map(PriceRankingData::from)
        .collect(Collectors.toList());
  }

  public List<PriceRankingData> findMaxPriceRankingByCategoryId(CategoryId categoryId) {
    return maxPriceRankingRepository.findByCategoryId(categoryId).stream()
        .map(PriceRankingData::from)
        .collect(Collectors.toList());
  }

  public List<PriceRankingData> findMinPriceRankingByBrandId(BrandId brandId) {
    return minPriceRankingRepository.findByBrandId(brandId).stream()
        .map(PriceRankingData::from)
        .collect(Collectors.toList());
  }
}
