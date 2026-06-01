package ir.aspireapps.springmart.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Standard API Error Response")
@Builder
public record ApiError(
        @Schema(example = "2026-05-18T12:30:00Z")
        Instant timestamp,

        @Schema(example = "404")
        int status,

        @Schema(example = "RESOURCE_NOT_FOUND")
        String code,

        @Schema(example = "Not Found")
        String error,

        @Schema(example = "Resource Not Found")
        String message,

        @Schema(example = "/api/v1/products/5")
        String path,

        Map<String, String> errors
) {
}
