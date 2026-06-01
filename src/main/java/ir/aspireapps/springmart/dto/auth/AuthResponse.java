package ir.aspireapps.springmart.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuthResponse",
        description = "Login success result contains tokens and expiration information")
public record AuthResponse(
        @Schema(description =  "Very secure access token for next requests",
                example = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiVVNFUiIs...")
        String accessToken,
        @Schema(description = "Very secure refresh token for next logging in requests",
                example = "oWHhPYtOmUR9omRExe2dX7OC9L1DY_CqoE2Lp0aXj...")
        String refreshToken,
        @Schema(description = "Type of token access security",
                example = "bearer")
        String tokenType,
        @Schema(description = "Time left to expiration of access token in seconds",
                example = "9000")
        long expirationIn
) {
}
