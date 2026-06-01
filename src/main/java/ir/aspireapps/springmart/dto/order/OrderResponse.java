package ir.aspireapps.springmart.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import ir.aspireapps.springmart.model.OrderState;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Schema(name = "OrderResponse", description = "Represents an order in the system")
public record OrderResponse(
        @Schema(description = "Unique order identifier", example = "5")
        long id,
        @Schema(description = "When the order created", example = "2026-06-01T10:15:30Z")
        Instant createdAt,
        @Schema(description = "When the order was last updated", example = "2026-06-01T10:15:30Z")
        Instant updatedAt,
        @Schema(description = "Total amount of order", example = "12.99")
        BigDecimal total,
        @Schema(description = """
                Current order state:
                - CONFIRMED: Order created
                - PAID: Payment completed
                - SHIPPED: order shipped
                - CANCELED: order canceled
                """)
        OrderState state,
        @Schema(description =  "Items included in order")
        List<OrderItemResponse> items
) {}