package musinsa_assignment.style_coordinator.common.exception;

public enum ApplicationErrorCode {
  SUCCESS("0000", "OK"),
  FAILURE("9999", "시스템 오류가 발생했습니다. 담당자에게 문의바랍니다."),
  NO_CATEGORY("C401", "존재하지 않는 카테고리 입니다."),
  NO_BRAND("B401", "존재하지 않는 브랜드 입니다."),
  NO_PRODUCT("P401", "존재하지 않는 상품 입니다."),
  ;

  private String code;
  private String message;

  ApplicationErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
