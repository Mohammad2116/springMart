package ir.aspireapps.springmart.dto.product;


import io.swagger.v3.oas.annotations.media.Schema;
import ir.aspireapps.springmart.model.Category;

import java.math.BigDecimal;

@Schema(name = "ProductResponse", description = "Product data returned by API")
public record ProductResponse(
        @Schema(description = "Product id", example = "1")
        Long id,
        @Schema(description = "Product name", example = "mouse")
        String name,
        @Schema(description = "Product description")
        String description,
        @Schema(description = "Units in stock", example = "100")
        Long stock,
        @Schema(description = "Product price", example = "19.99")
        BigDecimal price,
        @Schema(description = "Associated category")
        Category category
) {
}
