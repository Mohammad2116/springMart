package ir.aspireapps.springmart.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(
        long id,
        long productId,
        long productName,
        long quantity,
        BigDecimal price
) {
}
