package ir.aspireapps.springmart.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "OrderItemResponse",
        description = "Information on an item in an order")
public record OrderItemResponse(
        @Schema(description = "item Id in order", example = "5")
        long id,
        @Schema(description = "Id of Product as a snapshot", example = "5")
        long productId,
        @Schema(description = "Name of Product as a snapshot", example = "sample Product name")
        long productName,
        @Schema(description = "Quantity of product in order", example = "3")
        long quantity,
        @Schema(description = "Price of product in order as a snapshot of type BigDeciaml", example = "12")
        BigDecimal price
) {
}
