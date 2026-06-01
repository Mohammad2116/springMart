package ir.aspireapps.springmart.model;

import io.swagger.v3.oas.annotations.media.Schema;

public enum OrderState {
    CONFIRMED,
    PAYED,
    SHIPPED,
    CANCELED
}
