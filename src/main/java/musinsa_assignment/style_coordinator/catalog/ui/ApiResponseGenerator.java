package musinsa_assignment.style_coordinator.catalog.ui;

import musinsa_assignment.style_coordinator.common.exception.ApplicationErrorCode;

public class ApiResponseGenerator {

  public static final ApiResponse<Void> SUCCESS = new ApiResponse<>(ApplicationErrorCode.SUCCESS);
  public static final ApiResponse<Void> FAILURE = new ApiResponse<>(ApplicationErrorCode.FAILURE);

  private ApiResponseGenerator() {
    throw new IllegalStateException();
  }

  public static <D> ApiResponse success(D data) {
    return new ApiResponse(ApplicationErrorCode.SUCCESS, data);
  }

  public static <D> ApiResponse failure(D data) {
    return new ApiResponse(ApplicationErrorCode.FAILURE, data);
  }

  public static <D> ApiResponse of(String code, String message) {
    return new ApiResponse<>(code, message, null);
  }

  public static <D> ApiResponse of(String code, String message, D data) {
    return new ApiResponse<>(code, message, data);
  }

  public static <D> ApiResponse of(ApplicationErrorCode applicationResponseCode, D data) {
    return new ApiResponse<>(applicationResponseCode, data);
  }
}
