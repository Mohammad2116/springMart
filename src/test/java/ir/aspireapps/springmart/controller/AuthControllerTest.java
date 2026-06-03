package ir.aspireapps.springmart.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import ir.aspireapps.springmart.config.JwtFilter;
import ir.aspireapps.springmart.dto.auth.AuthResponse;
import ir.aspireapps.springmart.dto.auth.LoginRequest;
import ir.aspireapps.springmart.dto.user.UserRegistrationRequest;
import ir.aspireapps.springmart.error.CustomAuthenticationEntryPoint;
import ir.aspireapps.springmart.error.DuplicateResourceException;
import error.InvalidCredentialsException;
import ir.aspireapps.springmart.security.CustomUserDetailsService;
import ir.aspireapps.springmart.security.JwtService;
import ir.aspireapps.springmart.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthControl.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class
    })
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationEntryPoint authenticationEntryPoint = new CustomAuthenticationEntryPoint();

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthService authService;

    @MockitoBean
    private JwtFilter jwtFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    //////////////////////////////////////////////////////////////////////////// registration tests

    @Test
    void register_shouldReturn201() throws Exception {
        UserRegistrationRequest userRegistrationRequest =
                new UserRegistrationRequest(
                        "john@gmail.com",
                        "P@ssw0rd!",
                        "John",
                        "Doe");
        AuthResponse authResponse =
                new AuthResponse(
                        "access-token",
                        "refresh-token",
                        "bearer",
                        9000
                );

        when(authService.register(
                any(),
                any(),
                any()
        )).thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegistrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"))
                .andExpect(jsonPath("$.tokenType").value("bearer"))
                .andExpect(jsonPath("$.expirationIn").value("9000"));

        verify(authService).register(
                any(UserRegistrationRequest.class),
                any(),
                any()
        );
    }

    @Test
    void register_shouldReturn400_whenEmailIsBlank() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "",
                        "P@ssw0rd!",
                        "",
                        "Doe"
                );
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(), any(), any());
    }

    @Test
    void register_shouldReturn400_whenEmailIsInvalid() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "john.mail.",
                        "P@ssw0rd!",
                        "john",
                        "Doe"
                );
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(), any(), any());
    }

    @Test
    void register_shouldReturn400_whenPasswordIsBlank() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "john@mail.com",
                        "",
                        "john",
                        "Doe"
                );
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(), any(), any());
    }

    @Test
    void register_shouldReturn400_whenPasswordHasNoSpecialCharacter() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "john@mail.com",
                        "passw0rd",
                        "john",
                        "Doe"
                );
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(), any(), any());
    }

    @Test
    void register_shouldReturn400_whenPasswordIsTooShort() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "john@mail.com",
                        "p@1a",
                        "john",
                        "Doe"
                );
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(), any(), any());
    }

    @Test
    void register_shouldReturn400_whenPasswordHasNoNumber() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "john@mail.com",
                        "p@ssword!",
                        "john",
                        "Doe"
                );
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(), any(), any());
    }

    @Test
    void register_shouldReturn400_whenFirstNameIsBlank() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "john@mail.com",
                        "p@ssw0rd!",
                        "",
                        "Doe"
                );
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(), any(), any());
    }

    @Test
    void register_shouldReturn400_whenLastNameIsBlank() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "john@mail.com",
                        "p@ssw0rd!",
                        "John",
                        ""
                );
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(), any(), any());
    }

    @Test
    void register_shouldReturn409_whenEamilAlreadyExists() throws Exception {
        UserRegistrationRequest request =
                new UserRegistrationRequest(
                        "john@mail.com",
                        "p@ssw0rd!",
                        "John",
                        "Doe"
                );

        when(authService.register(any(), any(), any()))
                .thenThrow(new DuplicateResourceException("Email[" + request.email() + "] already exists"));


        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(authService).register(any(), any(), any());
    }

    //////////////////////////////////////////////////////////////////// Login tests

    @Test
    void login_shouldReturn200() throws Exception {
        LoginRequest loginRequest =
                new LoginRequest(
                        "john@gmail.com",
                        "P@ssw0rd!");
        AuthResponse authResponse =
                new AuthResponse(
                        "access-token",
                        "refresh-token",
                        "bearer",
                        9000
                );

        when(authService.login(any(), any(), any()))
            .thenReturn(authResponse);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"))
                .andExpect(jsonPath("$.tokenType").value("bearer"))
                .andExpect(jsonPath("$.expirationIn").value("9000"));

        verify(authService).login(
                any(LoginRequest.class),
                any(),
                any()
        );
    }

    @Test
    void login_shouldReturn401_whenUsernameOrPasswordIsInvalid() throws Exception {
        LoginRequest loginRequest =
                new LoginRequest(
                        "john@gmail.com",
                        "P@ssw0rd!");

        when(authService.login(any(), any(), any()))
                .thenThrow(new InvalidCredentialsException());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid username or password"))
                .andExpect(jsonPath("$.code").value("INVALID_CREDENTIALS"));

        verify(authService).login(
                any(LoginRequest.class),
                any(),
                any()
        );
    }

    @Test
    void login_shouldReturn400_whenInvalidEmailFormat() throws Exception {
        LoginRequest loginRequest =
                new LoginRequest(
                        "john.gmail.com",
                        "P@ssw0rd!");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).login(any(LoginRequest.class), any(), any());
    }

    @Test
    void login_shouldReturn400_whenEmptyPassword() throws Exception {
        LoginRequest loginRequest =
                new LoginRequest(
                        "john.gmail.com",
                        "");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).login(any(LoginRequest.class), any(), any());
    }

    @Test
    void login_shouldReturn400_whenNullRequestSent() throws Exception {
        LoginRequest loginRequest =
                new LoginRequest(
                        "",
                        "");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).login(any(LoginRequest.class), any(), any());
    }

    @Test
    void login_shouldReturn400_whenInvalidDataSent() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("INVALID DATA SENT")))
                .andExpect(status().isBadRequest());

        verify(authService, never()).login(any(LoginRequest.class), any(), any());
    }
}
