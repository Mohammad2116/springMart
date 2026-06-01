package ir.aspireapps.springmart.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(name = "ProductUpdateRequest", description = "Request to update an existing product")
public record ProductUpdateRequest(
        @Schema(description = "Product name", example = "Wireless Mouse")
        @NotBlank(message = "Product name can't be empty")
        @Size(min = 5, max = 255, message = "Product name must be between 5 to 255 characters")
        String name,

        @Schema(description = "Product description")
        @NotBlank(message = "Product's description can't be empty")
        @Size(min = 5, max = 1000, message = "Product's description must be between 5 to 1000 chracters")
        String description,

        @Schema(description = "Stock quantity", example = "100")
        @NotBlank(message = "Stock can't be empty")
        @PositiveOrZero(message = "Stock can't be negtive")
        @Max(value = 1000000, message = "Maximum amount of any product can't be more thant 1000000 items")
        Long stock,

        @Schema(description = "Product price", example = "19.99")
        @NotNull(message = "Price can't be empty")
        @PositiveOrZero(message = "Price can't be negative")
        @Max(value = 1000000000, message = "Maximum price of any item can't be more than 100000000")
        BigDecimal price,

        @Schema(description = "Category id", example = "2")
        @NotNull(message = "Category Id can't be empty")
        @Positive(message = "Category Id must be a positive and valid")
        Long categoryId
) {
}



