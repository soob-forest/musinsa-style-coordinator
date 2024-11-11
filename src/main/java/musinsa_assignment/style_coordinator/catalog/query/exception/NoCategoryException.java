package musinsa_assignment.style_coordinator.catalog.query.exception;

import static musinsa_assignment.style_coordinator.common.exception.ApplicationErrorCode.NO_CATEGORY;

import musinsa_assignment.style_coordinator.common.exception.ApplicationErrorCode;
import musinsa_assignment.style_coordinator.common.exception.ApplicationException;

public class NoCategoryException extends ApplicationException {

  public NoCategoryException() {
    super(NO_CATEGORY.getCode(), NO_CATEGORY.getMessage());
  }

  public NoCategoryException(String message) {
    super(NO_CATEGORY.getCode(), message);
  }

  @Override
  public ApplicationErrorCode getErrorCode() {
    return NO_CATEGORY;
  }
}
