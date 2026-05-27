package ir.aspireapps.springmart.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email can't be empty")
        @Email(message = "Please enter a valid email address")
        @Size(max = 255, message = "Maximum size of email is 255 characters")
        String email,

        @NotBlank(message = "Password can't be empty")
        @Size(max = 255, message = "Maximum size of password is 255 characters")
        String password
) {
}
