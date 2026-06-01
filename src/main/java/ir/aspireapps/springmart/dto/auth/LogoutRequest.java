package ir.aspireapps.springmart.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "LogoutRequest", description = "Logging out request by user")
public record LogoutRequest(
        @Schema(description = "Refresh token that received at last transition to JWT",
                example = "Very secure refresh token like: djfskfksljfklsfj.....")
        @NotBlank(message = "Refresh token value can't be empty")
        @Size(max = 500, message = "Refresh token value maximum size os 500 characters")
        String refreshToken
) {
}
