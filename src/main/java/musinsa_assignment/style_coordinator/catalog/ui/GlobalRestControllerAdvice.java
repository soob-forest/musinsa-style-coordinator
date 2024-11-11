package musinsa_assignment.style_coordinator.catalog.ui;

import lombok.extern.slf4j.Slf4j;
import musinsa_assignment.style_coordinator.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalRestControllerAdvice {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({ApplicationException.class})
  protected ApiResponse<Void> handle(ApplicationException applicationException) {
    log.error("[ApplicationException]", applicationException);
    return ApiResponseGenerator.of(applicationException.getCode(), applicationException.getMessage());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({Throwable.class})
  protected ApiResponse<Void> handle(Throwable throwable) {
    log.error("[UnknownException] Occur exception.", throwable);
    return ApiResponseGenerator.FAILURE;
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
      IllegalArgumentException.class,
      IllegalStateException.class,
  })
  protected ApiResponse<Void> handle(Exception exception) {
    log.error("[BadRequestException]", exception);
    return ApiResponseGenerator.of("C400", "[잘못된 요청] 요청내용을 확인하세요.");
  }
}
