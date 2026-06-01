package ir.aspireapps.springmart.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CategoryResponse", description = "Basic category info")
public record CategoryResponse(
        @Schema(description = "Category id", example = "1") Long id,
        @Schema(description = "Category name", example = "Electronics") String name
) {
}

