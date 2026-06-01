package ir.aspireapps.springmart.dto.user;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(name = "UserUpdateDetailsRequest", description = "Request to update user's basic details")
public record UserUpdateDetailsRequest(
        @Schema(description = "User first name", example = "John")
        @NotBlank(message = "First name can't be empty")
        @Size(max = 255, message = "Maximum size of first name is 255 characters")
        String firstName,

        @Schema(description = "User last name", example = "Doe")
        @NotBlank(message = "Last name can't be null")
        @Size(max = 255, message = "Maximum size of last name is 255 characters")
        String lastName
) {
}
