package ir.aspireapps.springmart.error;

import org.springframework.http.HttpStatus;

public class InsufficientProductStcok extends BusinessException {

    public InsufficientProductStcok(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE, "INSUFFICIENT_PRODUCT_STCOK");
    }
}