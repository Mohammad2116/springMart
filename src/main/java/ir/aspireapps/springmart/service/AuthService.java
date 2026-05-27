package ir.aspireapps.springmart.service;

import ir.aspireapps.springmart.config.JwtConfig;
import ir.aspireapps.springmart.dto.auth.AuthResponse;
import ir.aspireapps.springmart.dto.auth.LoginRequest;
import ir.aspireapps.springmart.dto.auth.RefreshRequest;
import ir.aspireapps.springmart.dto.user.UserRegistrationRequest;
import ir.aspireapps.springmart.model.RefreshToken;
import ir.aspireapps.springmart.model.User;
import ir.aspireapps.springmart.security.CustomUserDetails;
import ir.aspireapps.springmart.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public AuthResponse register(
            UserRegistrationRequest request, String deviceName, String deviceIP) {
        User user = userService.register(request);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.create(user, deviceName, deviceIP);
        return new AuthResponse(accessToken,
                refreshToken,
                jwtConfig.getTokenType(),
                jwtService.getAccessTokenExpirationSeconds());
    }

    @Transactional
    public AuthResponse login(LoginRequest request, String deviceName, String deviceIP) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()
                ));
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.user();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.create(customUserDetails.user(), deviceName, deviceIP);

        AuthResponse authResponse = new AuthResponse(
                accessToken,
                refreshToken,
                jwtConfig.getTokenType(),
                jwtService.getAccessTokenExpirationSeconds());
        return authResponse;
    }

    @Transactional
    public AuthResponse refresh(RefreshRequest request, String deviceName, String deviceIP) {
        RefreshToken oldToken = refreshTokenService.verify(request.refreshToken());

        User user = oldToken.getUser();

        refreshTokenService.markAsUsed(oldToken);
        refreshTokenService.revoke(oldToken);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.create(user, deviceName, deviceIP);

        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtConfig.getTokenType(),
                jwtService.getAccessTokenExpirationSeconds()
        );
    }

    public void logout(String refreshToken) {
        RefreshToken refreshtoken = refreshTokenService.verify(refreshToken);
        refreshTokenService.revoke(refreshtoken);
    }

    public void logoutAll(String email) {
        refreshTokenService.revokeAllUserTokens(email);
    }
}
