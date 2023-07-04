package RegistrationApp.dto.userDto;

import lombok.Data;

@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private int age;
}
