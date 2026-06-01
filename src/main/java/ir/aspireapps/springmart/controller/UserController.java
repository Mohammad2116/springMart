package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.aspireapps.springmart.dto.user.UserResponse;
import ir.aspireapps.springmart.dto.user.UserUpdateDetailsRequest;
import ir.aspireapps.springmart.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        name = "Users",
        description = """
                        User account endpoints — 
                        register, authenticate, 
                        manage profile updates, and account administration
                            """)
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Get User",
            description = "Retrieve details of a User account."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Details returned"
    )
    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> get(@Valid @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.get(user.getUsername()));
    }

    @Operation(
            summary = "Update User",
            description = "Update details information of a User account."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Details returned"
    )
    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @PutMapping("/me")
    public ResponseEntity<UserResponse> update(@Valid @AuthenticationPrincipal UserDetails user,
                                               @Valid @RequestBody UserUpdateDetailsRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.update(user.getUsername(), request));
    }

    @Operation(
            summary = "Set State",
            description = "Set User account state to active or deactive."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Details returned"
    )
    @PreAuthorize("hasAnyRole({'ADMIN'})")
    @PutMapping("/{id}")
    public ResponseEntity<Void> setStatus(@Valid @NotNull @PathVariable UUID id,
                                          boolean newStatus) {
        userService.setStatus(id, newStatus);
        return ResponseEntity.noContent().build();
    }
}
