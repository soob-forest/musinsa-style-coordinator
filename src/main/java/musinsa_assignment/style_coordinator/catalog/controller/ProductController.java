package musinsa_assignment.style_coordinator.catalog.controller;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.query.application.product.MinAndMaxPriceProductView;
import musinsa_assignment.style_coordinator.catalog.query.application.product.ProductQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

  private final ProductQueryService productQueryService;

  @GetMapping("/search/minAndMaxPriceProduct")
  public MinAndMaxPriceProductView searchMinAndMaxProductsByCategory(@RequestParam String category) {
    return productQueryService.searchMinAndMaxProductsByCategory(category);
  }
}
