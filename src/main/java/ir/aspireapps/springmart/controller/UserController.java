package ir.aspireapps.springmart.controller;

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

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> get(@Valid @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.get(user.getUsername()));
    }

    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @PutMapping("/me")
    public ResponseEntity<UserResponse> update(@Valid @AuthenticationPrincipal UserDetails user,
                                               @Valid @RequestBody UserUpdateDetailsRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
                userService.update(user.getUsername(), request));
    }

    @PreAuthorize("hasAnyRole({'ADMIN'})")
    @PutMapping("/{id}")
    public ResponseEntity<Void> setStatus(@Valid @NotNull @PathVariable UUID id,
                                          boolean newStatus) {
        userService.setStatus(id, newStatus);
        return ResponseEntity.noContent().build();
    }
}
