package musinsa_assignment.style_coordinator.catalog.ui.controller;

import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.query.application.category.CategoryQueryService;
import musinsa_assignment.style_coordinator.catalog.query.application.category.CheapestPerCategoryView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

  private final CategoryQueryService categoryQueryService;

  @GetMapping("/cheapestProducts")
  public CheapestPerCategoryView getCheapestPerCategoryView() {
    return categoryQueryService.getCheapestPerCategoryView();
  }
}
