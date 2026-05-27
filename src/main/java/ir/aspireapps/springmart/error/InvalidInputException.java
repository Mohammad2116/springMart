package ir.aspireapps.springmart.error;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends BusinessException {
    public InvalidInputException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INVALID_INPUT");
    }
}
