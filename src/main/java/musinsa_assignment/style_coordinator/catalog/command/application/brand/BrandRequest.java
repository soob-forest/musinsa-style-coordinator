package musinsa_assignment.style_coordinator.catalog.command.application.brand;

import jakarta.validation.constraints.NotBlank;

public record BrandRequest(@NotBlank(message = "브랜드 이름을 입력하세요") String name) {

}
