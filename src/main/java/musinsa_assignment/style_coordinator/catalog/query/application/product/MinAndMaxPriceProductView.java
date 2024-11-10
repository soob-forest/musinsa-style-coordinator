package musinsa_assignment.style_coordinator.catalog.query.application.product;

import java.util.List;

public record MinAndMaxPriceProductView(String categoryName, List<ProductSummary> minPrice, List<ProductSummary> maxPrice) {

}
