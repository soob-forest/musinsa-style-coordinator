package musinsa_assignment.style_coordinator.catalog.query.dao;

import java.util.List;
import java.util.Optional;
import musinsa_assignment.style_coordinator.catalog.domain.CategoryId;
import musinsa_assignment.style_coordinator.catalog.query.dto.CategoryData;
import org.springframework.data.repository.Repository;

public interface CategoryDao extends Repository<CategoryData, CategoryId> {

  List<CategoryData> findAll();

  Optional<CategoryData> findById(CategoryId categoryId);
}
