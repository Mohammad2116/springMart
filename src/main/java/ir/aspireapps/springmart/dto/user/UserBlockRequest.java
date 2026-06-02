package ir.aspireapps.springmart.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "UserBlockRequest",
        description = "Blocking request contains user and reason")
public record UserBlockRequest(
        @Schema(description = "Id of target user to be blocked",
                example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        @NotNull
        UUID userId,

        @Schema(description = "Reason of blocking",
                example = "To Many Failed Attamps To LogIn")
        @NotBlank
        String reason
        ) {}
