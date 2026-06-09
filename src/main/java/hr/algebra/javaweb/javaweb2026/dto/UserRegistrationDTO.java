package hr.algebra.javaweb.javaweb2026.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 100, message = "Password must contain at least 8 characters")
    private String password;

    @NotEmpty(message = "Confirm password cannot be empty")
    @Size(min = 8, max = 100, message = "Password must contain at least 8 characters")
    private String confirmPassword;
}
