package ir.aspireapps.springmart.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(name = "ProductRegisterRequest", description = "Request used to create a new product")
public record ProductRegisterRequest(
        @Schema(description = "Product name", example = "mouse")
        @NotBlank(message = "Product name can't be empty")
        @Size(min = 5, max = 255, message = "Product name must be between 5 to 255 characters")
        String name,

        @Schema(description = "Category id for product", example = "2")
        @NotBlank(message = "Category Id can't be empty")
        @Positive(message = "Not a valid Category Id")
        Long categoryId,

        @Schema(description = "Product description")
        @NotBlank(message = "Product's description can't be empty")
        @Size(min = 5, max = 1000, message = "Product's description must be between 5 to 1000 chracters")
        String description,

        @Schema(description = "Initial stock quantity", example = "100")
        @NotBlank(message = "Stock can't be empty")
        @PositiveOrZero(message = "Stock can't be negative")
        @Max(value = 1000000, message = "Maximum amount of any product can't be more than 1000000 items")
        Long stock,

        @Schema(description = "Product price", example = "19.99")
        @NotNull(message = "Price can't be empty")
        @PositiveOrZero(message = "Price can't be negative")
        @Max(value = 1000000000, message = "Maximum price of any item can't be more than 100000000")
        BigDecimal price
) {
}

