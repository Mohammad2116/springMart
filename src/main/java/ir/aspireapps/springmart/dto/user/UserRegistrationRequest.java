package ir.aspireapps.springmart.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotBlank(message = "Email address can't be null")
        @Email(message = "Please enter a valid email")
        @Size(max = 255, message = "Maximum size of email adderess is 255 characters")
        String email,

        @NotBlank(message = "Password can't be empty")
        @Size(max = 255, message = "Maximum size of password in 255 characters")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{};:'\\\",.<>/?\\\\|`~]).{8,}$",
                message = "Password must be at least 8 characters and include at least one number and one special character"
        )
        String password,

        @NotBlank(message = "First name can't be empty")
        @Size(max = 255, message = "Maximum size of first name is 255 characters")
        String firstName,


        @NotBlank(message = "Last name can't be null")
        @Size(max = 255, message = "Maximum size of last name is 255 characters")
        String lastName

) {
}
