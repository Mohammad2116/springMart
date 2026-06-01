package ir.aspireapps.springmart.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "RefreshTokenRequest", description = "A refresh token sent by user")
public record RefreshRequest(
        @Schema(description = "Refresh token that received at last transition to JWT",
                example = "Very secure token like: kjfkdlskfsdffdd....")
        @NotBlank(message = "Refresh token value can't be empty")
        @Size(max = 500, message = "Refresh token value maximum size os 500 characters")
        String refreshToken
) {
}

