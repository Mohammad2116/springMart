package ir.aspireapps.springmart.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import ir.aspireapps.springmart.model.Role;

import java.util.UUID;

@Schema(name = "UserResponse", description = "User details returned to client")
public record UserResponse(
        @Schema(description = "Unique user id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,
        @Schema(description = "User email", example = "user@example.com")
        String email,
        @Schema(description = "First name", example = "John")
        String firstName,
        @Schema(description = "Last name", example = "Doe")
        String lastName,
        @Schema(description = "User role")
        Role role
) {
}
