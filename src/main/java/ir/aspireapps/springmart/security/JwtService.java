package ir.aspireapps.springmart.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ir.aspireapps.springmart.config.JwtConfig;
import ir.aspireapps.springmart.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    private Claims extractAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token) {
        return extractAllClaimsFromToken(token).getSubject();
    }

    public UUID extractUserId(String token) {
        Object userId = extractAllClaimsFromToken(token).get("userId");
        return userId == null ? null : UUID.fromString(userId.toString());
    }

    public String extractRole(String token) {
        Object role = extractAllClaimsFromToken(token).get("role");
        return role == null ? null : role.toString();
    }

    public boolean isTokenValid(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public long getAccessTokenExpirationSeconds() {
        return jwtConfig.getAccessTokenExpiration() / 1000;
    }

    public String generateAccessToken(User user) {
        Date now = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().name());
        String accessToken = Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + getAccessTokenExpirationSeconds() * 1000))
                .signWith(getSigningKey())
                .compact();

        return accessToken;
    }
}
