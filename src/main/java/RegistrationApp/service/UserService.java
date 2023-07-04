package RegistrationApp.service;

import RegistrationApp.dto.userDto.UpdateUserDto;
import RegistrationApp.dto.userDto.UserDto;
import RegistrationApp.dto.userDto.UserInfoDto;
import RegistrationApp.dto.userDto.NewUserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserDto newUserDto);

    List<UserInfoDto> getAllUsers();

    UpdateUserDto updateUserById(Long id, UpdateUserDto updateUserDto);

    void deleteUserById(Long id);

    UserDto getUserByEmail(String email);
}
