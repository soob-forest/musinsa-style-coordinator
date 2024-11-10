package musinsa_assignment.style_coordinator.admin.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.command.application.brand.BrandRequest;
import musinsa_assignment.style_coordinator.catalog.command.application.brand.BrandResponse;
import musinsa_assignment.style_coordinator.catalog.command.application.brand.CreateBrandService;
import musinsa_assignment.style_coordinator.catalog.command.application.brand.DeleteBrandService;
import musinsa_assignment.style_coordinator.catalog.command.application.brand.UpdateBrandService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/api/v1/brands")
public class AdminBrandController {

  private final CreateBrandService createBrandService;
  private final UpdateBrandService updateBrandService;
  private final DeleteBrandService deleteBrandService;

  @PostMapping("")
  public BrandResponse create(@RequestBody @Valid BrandRequest brandRequest) {
    return createBrandService.create(brandRequest);
  }

  @PatchMapping("/{id}")
  public void update(@PathVariable String id, @RequestBody @Valid BrandRequest brandRequest) {
    updateBrandService.updateName(id, brandRequest);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    deleteBrandService.delete(id);
  }
}
