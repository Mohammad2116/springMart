package ir.aspireapps.springmart.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateRequest(
        @NotBlank(message = "Category name can't be empty")
        @Size(min = 5, max = 255, message = "Category name must be between 5 to 255 characters")
        String name
) {
}
