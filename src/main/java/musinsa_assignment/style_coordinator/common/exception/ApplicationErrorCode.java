package musinsa_assignment.style_coordinator.common.exception;

public enum ApplicationErrorCode {
  SUCCESS("0000", "OK"),
  FAILURE("9999", "시스템 오류가 발생했습니다. 담당자에게 문의바랍니다."),
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
