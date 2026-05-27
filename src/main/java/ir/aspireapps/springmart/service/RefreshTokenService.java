package ir.aspireapps.springmart.service;

import ir.aspireapps.springmart.config.JwtConfig;
import ir.aspireapps.springmart.error.InvalidTokenException;
import ir.aspireapps.springmart.model.RefreshToken;
import ir.aspireapps.springmart.model.User;
import ir.aspireapps.springmart.repo.RefreshTokenRepository;
import ir.aspireapps.springmart.repo.UserRepository;
import ir.aspireapps.springmart.security.TokenHashService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHashService tokenHashService;
    private final JwtConfig jwtConfig;
    private UserRepository userRepository;

    @Transactional
    public String create(User user, String device, String ip) {
        String rawToken = generateSecureToken();
        String hashedToken = tokenHashService.hashToken(rawToken);
        RefreshToken refreshToken = RefreshToken
                .builder()
                .tokenHash(hashedToken)
                .ip(ip)
                .device(device)
                .user(user)
                .expiresAt(Instant.now().plusMillis(jwtConfig.getRefreshTokenExpiration()))
                .used(false)
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    private String generateSecureToken() {
        byte[] randomizeToken = new byte[64];
        SecureRandom random = new SecureRandom();
        random.nextBytes(randomizeToken);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomizeToken);
    }

    public RefreshToken verify(String token) {
        String hash = tokenHashService.hashToken(token);

        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(hash)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.isRevoked())
            throw new InvalidTokenException("Token is revoked");
        if (refreshToken.isUsed())
            throw new InvalidTokenException("Token is used");
        if (refreshToken.getExpiresAt().isBefore(Instant.now()))
            throw new InvalidTokenException("Token expired");

        return refreshToken;
    }

    @Transactional
    public void revoke(RefreshToken token) {
        token.setRevoked(true);
        token.setRevokedAt(Instant.now());
    }

    @Transactional
    public void markAsUsed(RefreshToken token) {
        token.setUsed(true);
    }

    @Transactional
    public void revokeAllUserTokens(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        List<RefreshToken> tokens = refreshTokenRepository.findAllByUserAndRevokedFalse(user);
        tokens.forEach(token -> {
            revoke(token);
        });
    }
}
