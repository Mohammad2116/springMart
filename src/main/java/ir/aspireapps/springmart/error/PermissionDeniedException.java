package ir.aspireapps.springmart.error;

import org.springframework.http.HttpStatus;

public class PermissionDeniedException extends BusinessException {
    public PermissionDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN, "PERMISSION_DENIED");
    }
}
