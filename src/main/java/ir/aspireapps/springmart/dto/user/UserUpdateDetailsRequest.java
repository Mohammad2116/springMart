package ir.aspireapps.springmart.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateDetailsRequest(
        @NotBlank(message = "First name can't be empty")
        @Size(max = 255, message = "Maximum size of first name is 255 characters")
        String firstName,

        @NotBlank(message = "Last name can't be null")
        @Size(max = 255, message = "Maximum size of last name is 255 characters")
        String lastName
) {
}
