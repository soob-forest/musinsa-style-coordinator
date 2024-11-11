package musinsa_assignment.style_coordinator.catalog.query.exception;

import static musinsa_assignment.style_coordinator.common.exception.ApplicationErrorCode.NO_PRODUCT;

import musinsa_assignment.style_coordinator.common.exception.ApplicationErrorCode;
import musinsa_assignment.style_coordinator.common.exception.ApplicationException;

public class NoProductException extends ApplicationException {

  public NoProductException() {
    super(NO_PRODUCT.getCode(), NO_PRODUCT.getMessage());
  }

  @Override
  public ApplicationErrorCode getErrorCode() {
    return NO_PRODUCT;
  }
}
