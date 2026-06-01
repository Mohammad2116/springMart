package ir.aspireapps.springmart.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "CategoryUpdateRequest", description = "Update category details")
public record CategoryUpdateRequest(
        @Schema(description = "New name of category to edit to",
                example = "Example Category")
        @NotBlank(message = "Category name can't be empty")
        @Size(min = 5, max = 255, message = "Category name must be between 5 to 255 characters")
        String name
) {
}
