package ir.aspireapps.springmart.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(name = "UserPasswordUpdateRequest", description = "Request to change user's password")
public record UserPasswordUpdateRequest(
        @Schema(description = "Current/old password", example = "OldP@ss1")
        @NotBlank(message = "Old password can't be empty")
        @Size(max = 255, message = "Maximum size of old password is 255 characters")
        String oldPassword,

        @Schema(description = "New password", example = "N3wP@ssw0rd")
        @NotBlank(message = "New password can't be empty")
        @Size(max = 255, message = "Maximum size of password is 255 characters")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{};:'\\\",.<>/?\\\\|`~]).{8,}$",
                message = "New password must be at least 8 characters and include at least one number and one special character"
        )
        String newPassword,

        @Schema(description = "Confirm new password", example = "N3wP@ssw0rd")
        @NotBlank(message = "Confirm password can't be empty")
        @Size(max = 255, message = "Maximum size of confirm new password in 255 characters")
        String confirmNewPassword
) {
}
