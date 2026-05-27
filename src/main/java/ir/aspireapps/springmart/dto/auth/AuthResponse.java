package ir.aspireapps.springmart.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expirationIn
) {
}
