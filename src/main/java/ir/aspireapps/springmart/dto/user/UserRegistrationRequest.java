package ir.aspireapps.springmart.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(name = "UserRegistrationRequest", description = "Request to register a new user")
public record UserRegistrationRequest(
        @Schema(description = "User email address", example = "user@example.com")
        @NotBlank(message = "Email address can't be null")
        @Email(message = "Please enter a valid email")
        @Size(max = 255, message = "Maximum size of email adderess is 255 characters")
        String email,

        @Schema(description = "User password (at least 8 chars, include number and special char)", example = "P@ssw0rd!")
        @NotBlank(message = "Password can't be empty")
        @Size(max = 255, message = "Maximum size of password in 255 characters")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{};:'\\\",.<>/?\\\\|`~]).{8,}$",
                message = "Password must be at least 8 characters and include at least one number and one special character"
        )
        String password,

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
