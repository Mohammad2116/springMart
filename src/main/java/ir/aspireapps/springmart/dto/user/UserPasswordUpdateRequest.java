package ir.aspireapps.springmart.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserPasswordUpdateRequest(
        @NotBlank(message = "Old password can't be empty")
        @Size(max = 255, message = "Maximum size of old password is 255 characters")
        String oldPassword,

        @NotBlank(message = "New password can't be empty")
        @Size(max = 255, message = "Maximum size of password is 255 characters")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{};:'\\\",.<>/?\\\\|`~]).{8,}$",
                message = "New password must be at least 8 characters and include at least one number and one special character"
        )
        String newPassword,

        @NotBlank(message = "Confirm password can't be empty")
        @Size(max = 255, message = "Maximum size of confirm new password in 255 characters")
        String confirmNewPassword
) {
}
