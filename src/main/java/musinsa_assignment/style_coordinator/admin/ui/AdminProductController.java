package musinsa_assignment.style_coordinator.admin.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.command.application.product.CreateProductService;
import musinsa_assignment.style_coordinator.catalog.command.application.product.DeleteProductService;
import musinsa_assignment.style_coordinator.catalog.command.application.product.ProductRequest;
import musinsa_assignment.style_coordinator.catalog.command.application.product.ProductResponse;
import musinsa_assignment.style_coordinator.catalog.command.application.product.UpdateProductRequest;
import musinsa_assignment.style_coordinator.catalog.command.application.product.UpdateProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/api/v1/products")
public class AdminProductController {

  private final CreateProductService createProductService;
  private final UpdateProductService updateProductService;
  private final DeleteProductService deleteProductService;

  @PostMapping("")
  public ProductResponse create(@RequestBody @Valid ProductRequest productRequest) {
    return createProductService.create(productRequest);
  }

  @PatchMapping("/{id}")
  public void update(@PathVariable String id, @RequestBody UpdateProductRequest updateProductRequest) {
    updateProductService.update(id, updateProductRequest);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id) {
    deleteProductService.delete(id);
  }

}
