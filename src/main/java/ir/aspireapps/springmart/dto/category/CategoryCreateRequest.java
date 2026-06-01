package ir.aspireapps.springmart.dto.category;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(name = "CategoryCreateRequest", description = "Basic information of a new category to create.")
public record CategoryCreateRequest(
        @Schema(description = "Name of new category",
                example = "New Category")
        @NotBlank(message = "Category name can't be empty")
        @Size(min = 5, max = 255, message = "Category name must be between 5 to 255 characters")
        String name
) {
}

