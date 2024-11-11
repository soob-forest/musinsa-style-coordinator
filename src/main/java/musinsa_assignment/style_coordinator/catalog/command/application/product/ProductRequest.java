package musinsa_assignment.style_coordinator.catalog.command.application.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;


public record ProductRequest(@NotBlank(message = " 카테고리를 입력하세요") String categoryId,
                             @NotBlank(message = "브랜드를 입력하세요") String brandId,
                             @NotNull(message = "가격을 입력하세요") BigDecimal price) {

}
