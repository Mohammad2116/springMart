package ir.aspireapps.springmart.error;

import org.springframework.http.HttpStatus;

public class RedundantActionException extends BusinessException {
    public RedundantActionException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "REDUNDUNT_ACTION_EXCEPTION");
    }
}
