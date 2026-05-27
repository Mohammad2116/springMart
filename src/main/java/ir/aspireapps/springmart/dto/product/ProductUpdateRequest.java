package ir.aspireapps.springmart.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @NotBlank(message = "Product name can't be empty")
        @Size(min = 5, max = 255, message = "Product name must be between 5 to 255 characters")
        String name,

        @NotBlank(message = "Product's description can't be empty")
        @Size(min = 5, max = 1000, message = "Product's description must be between 5 to 1000 chracters")
        String description,

        @NotBlank(message = "Stock can't be empty")
        @PositiveOrZero(message = "Stock can't be negtive")
        @Max(value = 1000000, message = "Maximum amount of any product can't be more thant 1000000 items")
        Long stock,

        @NotNull(message = "Price can't be empty")
        @PositiveOrZero(message = "Price can't be negative")
        @Max(value = 1000000000, message = "Maximum price of any item can't be more than 100000000")
        BigDecimal price,

        @NotNull(message = "Category Id can't be empty")
        @Positive(message = "Category Id must be a positive and valid")
        Long categoryId
) {
}



