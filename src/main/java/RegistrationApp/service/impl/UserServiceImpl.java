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
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
         Period dateRange = Period.between(userDto.getBirthDate(), LocalDate.now());
         dateRange.getYears();

        User user = modelMapper.map(userDto, User.class);
            return modelMapper.map(userRepo.save(user), UserDto.class);


    }

    @Override
    public Page<UserDto> getAllUsers(String localDateFrom, String localDateTo, Pageable pageable) {
        Page<User> users = null;

        if (StringUtils.isEmpty(localDateFrom) || StringUtils.isEmpty(localDateTo)){
             users =  userRepo.findAll(pageable);
            return users.map(user -> modelMapper.map(user, UserDto.class));
        }
        users =  userRepo.findByBirthDateBetween(LocalDate.parse(localDateFrom), LocalDate.parse(localDateTo), pageable);
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public UserDto updateUserById(Long id, UserDto userDto){
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
        modelMapper.map(user, user);
        User updatedUser = userRepo.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUserById(Long id) {
        if(userRepo.existsById(id)) {
            userRepo.deleteById(id);
        }else {
            throw new NotFoundException("User with this id not found!");
        }
    }
}
