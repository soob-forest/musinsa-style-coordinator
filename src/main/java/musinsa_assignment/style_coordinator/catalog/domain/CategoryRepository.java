package musinsa_assignment.style_coordinator.catalog.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CategoryRepository extends Repository<Category, CategoryId> {

  Optional<Category> findById(CategoryId id);
}
