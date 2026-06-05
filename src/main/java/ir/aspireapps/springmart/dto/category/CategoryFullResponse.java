package ir.aspireapps.springmart.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import ir.aspireapps.springmart.dto.product.ProductResponse;

import java.io.Serializable;
import java.util.List;

@Schema(name = "CategoryFullResponse", description = "Category with its products")
public record CategoryFullResponse(
        @Schema(description = "Category id", example = "1")
        Long id,
        @Schema(description = "Category name", example = "Electronics")
        String name,
        @Schema(description = "Products under this category")
        List<ProductResponse> products
) implements Serializable {
}
