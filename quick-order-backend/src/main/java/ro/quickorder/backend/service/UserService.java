package ro.quickorder.backend.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.UserAttributeConverter;
import ro.quickorder.backend.converter.UserConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotAcceptableException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserAttributeConverter userAttributeConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttributeRepository userAttributeRepository;

    public UserDto login(UserDto userDto) {
        User convertedUser = userConverter.toUser(userDto);
        User user = userRepository.findAll().stream()
                .filter(filteredUser -> convertedUser.getPassword().equals(filteredUser.getPassword()) &&
                        convertedUser.getUsername().equals(filteredUser.getUsername()))
                .findFirst().orElse(null);
        if (user != null) {
            return new UserDto(user.getUsername(), user.getEmail(), userAttributeConverter.toUserAttributeDto(user.getAttribute()));
        } else {
            LOG.error("User or password are incorrect!");
            throw new NotFoundException("User or password are incorrect!");
        }
    }

    public UserDto signUp(UserDto userDto) {
        if (userDto == null) {
            LOG.error("User is null!");
            throw new BadRequestException("User is null!");
        }
        String line = userDto.getUsername();
        String pattern = "^[a-zA-Z0-9_.]{5,}$";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        Matcher m = r.matcher(line);
        // bad username
        if (!m.find()) {
            LOG.error("UserName has characters that are not allowed!");
            throw new ForbiddenException("UserName has characters that are not allowed!");
        }
        // test if username is ok
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            LOG.error("UserName is already taken!");
            throw new NotAcceptableException("UserName is already taken!");
        }
        // test if email is ok
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            LOG.error("Email is already taken!");
            throw new NotAcceptableException("Email is already taken!");
        }
        User user = userConverter.toUser(userDto);
        User newUser = userRepository.save(user);
        UserAttribute userAttribute = new UserAttribute();
        userAttributeRepository.save(userAttribute);
        userAttribute.setUser(newUser);
        UserAttribute newAttribute = userAttributeRepository.save(userAttribute);
        newUser.setAttribute(newAttribute);
        return new UserDto(newUser.getUsername(), newUser.getEmail(),
                userAttributeConverter.toUserAttributeDto(newUser.getAttribute()));
    }
}
