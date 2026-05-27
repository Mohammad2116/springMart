package ir.aspireapps.springmart.controller;

import ir.aspireapps.springmart.dto.auth.AuthResponse;
import ir.aspireapps.springmart.dto.auth.LoginRequest;
import ir.aspireapps.springmart.dto.auth.LogoutRequest;
import ir.aspireapps.springmart.dto.auth.RefreshRequest;
import ir.aspireapps.springmart.dto.user.UserRegistrationRequest;
import ir.aspireapps.springmart.security.CustomUserDetails;
import ir.aspireapps.springmart.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@AllArgsConstructor
public class AuthControl {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest,
                                                 HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authService.register(
                        userRegistrationRequest,
                        httpServletRequest.getHeader("User-Agent"),
                        httpServletRequest.getRemoteAddr()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest,
                                              HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                authService.login(
                        loginRequest,
                        httpServletRequest.getHeader("User-Agent"),
                        httpServletRequest.getRemoteAddr()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest refreshRequest,
                                                HttpServletRequest servletRequest) {
        return ResponseEntity.ok(
                authService.refresh(
                        refreshRequest,
                        servletRequest.getHeader("User-Agent"),
                        servletRequest.getRemoteAddr()
                )
        );
    }

    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request.refreshToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> logoutAll(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        authService.logoutAll(customUserDetails.user().getEmail());
        return ResponseEntity.ok().build();
    }
}
