package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.aspireapps.springmart.dto.auth.AuthResponse;
import ir.aspireapps.springmart.dto.auth.LoginRequest;
import ir.aspireapps.springmart.dto.auth.LogoutRequest;
import ir.aspireapps.springmart.dto.auth.RefreshRequest;
import ir.aspireapps.springmart.dto.error.StandardErrors;
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

@Tag(
        name = "Authentication",
        description = "User authentication and JWT management"
)
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@AllArgsConstructor
public class AuthControl {
    private final AuthService authService;

    @Operation(
            summary = "Register",
            description = "Register new user and returns required tokens in a AuthResponse as result."
    )
    @ApiResponse(
            responseCode = "201",
            description = "User successfully registered"
    )
    @StandardErrors
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Parameter(
                    description = "New user information for registration"
            )
            @Valid @RequestBody UserRegistrationRequest userRegistrationRequest,
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authService.register(
                        userRegistrationRequest,
                        httpServletRequest.getHeader("User-Agent"),
                        httpServletRequest.getRemoteAddr()));
    }

    @Operation(
            summary = "User Login",
            description = "User Login endpoint, It will return a AuthResponse as result."
    )
    @ApiResponse(
            responseCode = "201",
            description = "User successfully logged-in"
    )
    @StandardErrors
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(
                    description = "User login information"
            )
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                authService.login(
                        loginRequest,
                        httpServletRequest.getHeader("User-Agent"),
                        httpServletRequest.getRemoteAddr()));
    }

    @Operation(
            summary = "Refresh Tokens",
            description = """
                            Generates new Access and Refresh tokens and return new tokens in a AuthResponse as result.
                            """
    )
    @ApiResponse(
            responseCode = "201",
            description = "Tokens successfully refreshed"
    )
    @StandardErrors
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @Parameter(
                    description = "Refresh token request"
            )
            @Valid @RequestBody RefreshRequest refreshRequest,
                                                HttpServletRequest servletRequest) {
        return ResponseEntity.ok(
                authService.refresh(
                        refreshRequest,
                        servletRequest.getHeader("User-Agent"),
                        servletRequest.getRemoteAddr()
                )
        );
    }

    @Operation(
            summary = "Log out ",
            description = """
                Logged out current device and invoked device's refresh token.
                
                Authorization:
                - USER or ADMIN role required
                """
    )
    @ApiResponse(
            responseCode = "201",
            description = "User logged out successfully"
    )
    @StandardErrors
    @SecurityRequirement(name = "bearer Authentication")
    @PostMapping("/logout")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> logout(
            @Parameter(
                    description = "Logout request must carry last Refresh token for current device to be invoked."
            )
            @Valid @RequestBody LogoutRequest request) {
        authService.logout(request.refreshToken());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Log out all",
            description = """
                Logged out all devices and invoked all refresh tokens.
    
                Authorization:
                - USER or ADMIN role required
                """
            
    )
    @ApiResponse(
            responseCode = "201",
            description = "User logged out successfully"
    )
    @StandardErrors
    @SecurityRequirement(name = "bearer Authentication")
    @PostMapping("/logout/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> logoutAll(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        authService.logoutAll(customUserDetails.user().getEmail());
        return ResponseEntity.ok().build();
    }
}
