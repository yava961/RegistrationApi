package RegistrationApp.service.impl;

import RegistrationApp.dto.userDto.NewUserDto;
import RegistrationApp.dto.userDto.UpdateUserDto;
import RegistrationApp.dto.userDto.UserDto;
import RegistrationApp.dto.userDto.UserInfoDto;
import RegistrationApp.entity.User;
import RegistrationApp.exeption.NotFoundException;
import RegistrationApp.repository.UserRepository;
import RegistrationApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(NewUserDto newUserDto) {
        User user = modelMapper.map(newUserDto, User.class);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        var users = (List<User>) userRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UpdateUserDto updateUserById(Long id, UpdateUserDto updateUserDto){
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
        modelMapper.map(updateUserDto, user);
        User updatedUser = userRepo.save(user);
        return modelMapper.map(updatedUser, UpdateUserDto.class);
    }

    @Override
    public void deleteUserById(Long id) {
        if(userRepo.existsById(id)) {
            userRepo.deleteById(id);
        }else {
            throw new NotFoundException("User with this id not found!");
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("User with this mail not found!"));
        return modelMapper.map(user, UserDto.class);
    }
}
