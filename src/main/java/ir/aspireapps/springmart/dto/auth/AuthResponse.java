package ir.aspireapps.springmart.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AuthResponse",
        description = "Login success result contains tokens and expiration information")
public record AuthResponse(
        @Schema(description =  "Very secure access token for next requests",
                example = "Very-Secure-Token: djfkdsjfkslfjdsfjdksfjkds....")
        String accessToken,
        @Schema(description = "Very secure refresh token for next logging in requests",
                example = "Very-Secure-Token: dfsdkfjsdkfjskdf.....")
        String refreshToken,
        @Schema(description = "Type of token access security",
                example = "bearer")
        String tokenType,
        @Schema(description = "Time left to expiration of access token in seconds",
                example = "9000")
        long expirationIn
) {
}
