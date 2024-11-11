package musinsa_assignment.style_coordinator.catalog.command.application.product;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import musinsa_assignment.style_coordinator.catalog.domain.ProductId;
import musinsa_assignment.style_coordinator.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteProductService {

  private final ProductRepository productRepository;

  @Transactional
  public void delete(String id) {
    var product = productRepository.findById(ProductId.of(id)).orElseThrow(NoSuchElementException::new);
    productRepository.delete(product);
  }
}
