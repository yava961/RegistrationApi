package RegistrationApp.service.impl;

import RegistrationApp.dto.user.UserDto;
import RegistrationApp.entity.User;
import RegistrationApp.exeption.NotFoundException;
import RegistrationApp.repository.UserRepository;
import RegistrationApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        var user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public Page<UserDto> getAllUsers(String localDateFrom, String localDateTo, Pageable pageable) {
        Page<User> users = null;

        if (StringUtils.isEmpty(localDateFrom) || StringUtils.isEmpty(localDateTo)) {
            users = userRepo.findAll(pageable);
            return users.map(user -> modelMapper.map(user, UserDto.class));
        }
        LocalDate from = LocalDate.parse(localDateFrom);
        LocalDate to = LocalDate.parse(localDateTo);
        if(from.compareTo(to) >= 0 ){
            throw new IllegalArgumentException("From date must be less than To date");
        }
        users = userRepo.findByBirthDateBetween(from, to, pageable);
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public UserDto updateUserById(Long id, UserDto userDto) {
        var user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthDate(userDto.getBirthDate());
        var updatedUser = userRepo.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        } else {
            throw new NotFoundException("User with this id not found!");
        }
    }
}
