package ro.quickorder.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.convertors.UserConvertor;
import ro.quickorder.backend.exception.*;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;

import javax.inject.Inject;

@Service
public class UserService {

    @Autowired
    private UserConvertor userConvertor;

    @Autowired
    private UserRepository userRepository;
    @Inject
    private UserAttributeRepository userAttributeRepository;

    public UserDto login(UserDto userDto) {
        User user = userConvertor.convertUserDtoToUser(userDto);
        for (User u : userRepository.findAll()) {
            if (user.getPassword().equals(u.getPassword()) && user.getUsername().equals(u.getUsername())) {
                return new UserDto(u.getId(),u.getUsername(),u.getEmail(), new UserAttributeDto(u.getAttribute()));
            }
        }
        throw new NotFoundException("User or password are incorrect!");
    }
}
