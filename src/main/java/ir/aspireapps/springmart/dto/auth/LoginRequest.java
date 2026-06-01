package ir.aspireapps.springmart.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "LoginRequest", description = "Request to loggin to system")
public record LoginRequest(
        @Schema(description = "Email address of user as username to log in",
                example = "John@gmail.com")
        @NotBlank(message = "Email can't be empty")
        @Email(message = "Please enter a valid email address")
        @Size(max = 255, message = "Maximum size of email is 255 characters")
        String email,

        @Schema(description = "User password to log into system",
                example = "P@ssw0rd!")
        @NotBlank(message = "Password can't be empty")
        @Size(max = 255, message = "Maximum size of password is 255 characters")
        String password
) {
}
