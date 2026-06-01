package ir.aspireapps.springmart.dto.error;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(
                responseCode = "400",
                description = "Validation Error"),
        @ApiResponse(
                responseCode = "401",
                description = "Unauthorized"),
        @ApiResponse(
                responseCode = "403",
                description = "Forbidden"),
        @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error")
})
public @interface StandardErrors {
}