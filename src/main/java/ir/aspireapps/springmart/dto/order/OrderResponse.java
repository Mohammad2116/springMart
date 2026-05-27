package ir.aspireapps.springmart.dto.order;

import ir.aspireapps.springmart.model.OrderState;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        long id,
        Instant createdAt,
        Instant updatedAt,
        BigDecimal total,
        OrderState state,
        List<OrderItemResponse> items
) {
}
