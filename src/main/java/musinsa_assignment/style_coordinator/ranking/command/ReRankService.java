package musinsa_assignment.style_coordinator.ranking.command;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.BrandId;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.common.domain.Money;
import musinsa_assignment.style_coordinator.ranking.domain.MaxPriceRanking;
import musinsa_assignment.style_coordinator.ranking.domain.MaxPriceRankingRepository;
import musinsa_assignment.style_coordinator.ranking.domain.MinPriceRanking;
import musinsa_assignment.style_coordinator.ranking.domain.MinPriceRankingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReRankService {

  private final MinPriceRankingRepository minPriceRankingRepository;
  private final MaxPriceRankingRepository maxPriceRankingRepository;

  public void reRank(CategoryId categoryId, BrandId brandId, ProductId productId, Money price) {
    // 최대 가격 랭킹 업데이트
    rankMaxPrice(categoryId, brandId, productId, price);
    // 최소 가격 랭킹 업데이트
    rankMinPrice(categoryId, brandId, productId, price);

  }

  private void rankMaxPrice(CategoryId categoryId, BrandId brandId, ProductId productId, Money price) {
    var maxRankings = maxPriceRankingRepository.findByCategoryId(categoryId);
    if (maxRankings.isEmpty()) {
      maxPriceRankingRepository.save(MaxPriceRanking.builder().categoryId(categoryId).brandId(brandId).productId(productId).price(price).build());
    } else {
      Money currentMaxPrice = maxRankings.get(0).getPrice();

      if (price.isGreaterThan(currentMaxPrice)) {

        maxPriceRankingRepository.deleteAllInBatch();
        maxPriceRankingRepository.save(MaxPriceRanking.builder().categoryId(categoryId).brandId(brandId).productId(productId).price(price).build());
      }

      // 동일한 가격인 경우 새로운 랭킹 추가
      if (price.equals(currentMaxPrice)) {

        maxPriceRankingRepository.save(MaxPriceRanking.builder().categoryId(categoryId).brandId(brandId).productId(productId).price(price).build());
      }
    }
  }

  private void rankMinPrice(CategoryId categoryId, BrandId brandId, ProductId productId, Money price) {
    var minRankings = minPriceRankingRepository.findByCategoryId(categoryId);
    if (minRankings.isEmpty()) {
      minPriceRankingRepository.save(MinPriceRanking.builder().categoryId(categoryId).brandId(brandId).productId(productId).price(price).build());

    } else {
      Money currentMinPrice = minRankings.get(0).getPrice();

      // 기존 최소 가격 랭킹들을 모두 삭제하고 새로운 최소 가격 랭킹 저장
      if (price.isLessThan(currentMinPrice)) {

        minPriceRankingRepository.deleteAllInBatch();
        minPriceRankingRepository.save(MinPriceRanking.builder().categoryId(categoryId).brandId(brandId).productId(productId).price(price).build());
      }

      // 동일한 가격인 경우 새로운 랭킹 추가
      if (price.equals(currentMinPrice)) {

        minPriceRankingRepository.save(MinPriceRanking.builder().categoryId(categoryId).brandId(brandId).productId(productId).price(price).build());
      }
    }
  }
}
