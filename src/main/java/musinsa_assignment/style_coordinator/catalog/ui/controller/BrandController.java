package musinsa_assignment.style_coordinator.catalog.ui.controller;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.query.application.brand.BrandQueryService;
import musinsa_assignment.style_coordinator.catalog.query.application.brand.CheapestBrandView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/brands")
public class BrandController {

  private final BrandQueryService brandQueryService;

  @GetMapping("/cheapest")
  public CheapestBrandView getCheapestBrandView() {
    return brandQueryService.getCheapestCategoryBundleView();
  }
}
