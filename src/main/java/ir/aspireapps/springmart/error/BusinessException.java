package ir.aspireapps.springmart.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String code;

    public BusinessException(String message, HttpStatus status, String code) {
        super(message);
        this.httpStatus = status;
        this.code = code;
    }
}
