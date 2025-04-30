package my.com.azwa.recipe.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PhisException extends RuntimeException {
  private Exception exception;
  private String moduleName;
  private String errorDesc;

  public PhisException(
      String moduleName, Exception exception, String errorDesc) {
    this.exception = exception;
    this.moduleName = moduleName;
    this.errorDesc = errorDesc;
  }

  public Exception exception() {
    return exception;
  }

  public String moduleName() {
    return moduleName;
  }

  public String errorDesc() {
    return errorDesc;
  }

  public ProblemDetail response(HttpStatus status) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    problemDetail.setTitle("RECIPE MANAGEMENT SYSTEM");
    if (exception != null) {
      problemDetail.setDetail(exception.getMessage());
    } else {
      problemDetail.setDetail(errorDesc);
    }
    // problemDetail.setProperty("uri", request.getDescription(false));
    problemDetail.setProperty("timestamp", LocalDateTime.now());
    problemDetail.setProperty("code", HttpStatus.INTERNAL_SERVER_ERROR.name());
    // problemDetail.setProperty("messageDetails", CommonUtil.getErrorInString(ex));
    return problemDetail;
  }
}
