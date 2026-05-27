package ir.aspireapps.springmart.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LogoutRequest(
        @NotBlank(message = "Refresh token value can't be empty")
        @Size(max = 500, message = "Refresh token value maximum size os 500 characters")
        String refreshToken
) {
}
