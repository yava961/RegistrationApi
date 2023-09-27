package RegistrationApp.service;

import RegistrationApp.dto.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;


public interface UserService {

    UserDto createUser(UserDto userDto);


    Page<UserDto> getAllUsers(String localDateFrom, String localDateTo, Pageable pageable);

    UserDto updateUserById(Long id, UserDto userDto);

    void deleteUserById(Long id);

}
