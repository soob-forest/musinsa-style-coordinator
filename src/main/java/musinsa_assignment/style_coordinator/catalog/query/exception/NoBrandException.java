package musinsa_assignment.style_coordinator.catalog.query.exception;

import static musinsa_assignment.style_coordinator.common.exception.ApplicationErrorCode.NO_BRAND;

import musinsa_assignment.style_coordinator.common.exception.ApplicationErrorCode;
import musinsa_assignment.style_coordinator.common.exception.ApplicationException;

public class NoBrandException extends ApplicationException {

  public NoBrandException() {
    super(NO_BRAND.getCode(), NO_BRAND.getMessage());
  }

  public NoBrandException(String message) {
    super(NO_BRAND.getCode(), message);
  }

  @Override
  public ApplicationErrorCode getErrorCode() {
    return NO_BRAND;
  }
}
