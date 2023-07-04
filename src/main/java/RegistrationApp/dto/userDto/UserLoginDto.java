package RegistrationApp.dto.userDto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserLoginDto {

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$",
            message = "Invalid email format")
    private String username;
    private String password;
}
