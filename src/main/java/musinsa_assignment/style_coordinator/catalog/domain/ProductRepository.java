package musinsa_assignment.style_coordinator.catalog.domain;

import java.util.Optional;
import musinsa_assignment.style_coordinator.common.domain.NanoIdGenerator;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface ProductRepository extends Repository<Product, ProductId> {

  Optional<Product> findById(ProductId id);

  void save(Product brand);

  void delete(Product product);

  @Modifying(clearAutomatically = true, flushAutomatically = true)
  @Query("delete from Product p where p.brandId = :brandId")
  void deleteAllByBrandId(BrandId brandId);

  default ProductId nextId() {
    IdGenerator idGenerator = new NanoIdGenerator();
    return ProductId.of(idGenerator.generateId());
  }


}
