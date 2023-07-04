package RegistrationApp.web;

import RegistrationApp.dto.userDto.NewUserDto;
import RegistrationApp.dto.userDto.UpdateUserDto;
import RegistrationApp.service.UserService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityScheme(name = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createUser(@RequestBody @Validated NewUserDto newUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(newUserDto));
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<UpdateUserDto> updateUser(@PathVariable Long id, @Validated @RequestBody UpdateUserDto updateUserDto) {
        UpdateUserDto updateUserById = userService.updateUserById(id, updateUserDto);
        return ResponseEntity.ok(updateUserById);
    }

    @GetMapping("/all_users")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "jwt")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/email/{userEmail}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity findUserByEmail(@PathVariable String userEmail) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByEmail(userEmail));
    }

    @DeleteMapping("/delete/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "jwt")
    public ResponseEntity deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User with id: " + userId + " deleted\"}");
    }
}
