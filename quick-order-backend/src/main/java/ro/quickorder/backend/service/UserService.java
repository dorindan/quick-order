package ro.quickorder.backend.service;


import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.UserAttributeConverter;
import ro.quickorder.backend.converter.UserConverter;
import ro.quickorder.backend.exception.*;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserAttributeConverter userAttributeConverter;

    @Autowired
    private UserRepository userRepository;

    @Inject
    private UserAttributeRepository userAttributeRepository;

    public UserDto login(UserDto userDto) {
        User user = userConverter.toUser(userDto);
        for (User u : userRepository.findAll()) {
            if (user.getPassword().equals(u.getPassword()) && user.getUsername().equals(u.getUsername())) {
                return new UserDto(u.getUsername(),u.getEmail(), userAttributeConverter.toUserAttributeDto(u.getAttribute()));
            }
        }
        throw new NotFoundException("User or password are incorrect!");
    }

    public UserDto signUp(UserDto userDto) {

        String line = userDto.getUsername();
        String pattern = "^[a-zA-Z0-9_.]*$";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);
        // bad username
        if (!m.find()) {
            throw new ForbiddenException("UserName has characters that are not allowed!");
        }
        // test if username is ok
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new NotAcceptableException("UserName is already taken!");
        }
        // test if email is ok
            if (userRepository.findByEmail(userDto.getEmail()) != null) {
                throw new NotAcceptableException("Email is already taken!");
            }

        User user = userConverter.toUser(userDto);
        User newUser = userRepository.save(user);
        UserAttribute userAttribute = new UserAttribute();
        userAttributeRepository.save(userAttribute);
        userAttribute.setUser(newUser);
        UserAttribute newAttribute = userAttributeRepository.save(userAttribute);
        newUser.setAttribute(newAttribute);

        UserDto userDtoRez = new UserDto(newUser.getUsername(), newUser.getEmail(),
                userAttributeConverter.toUserAttributeDto(newUser.getAttribute()));
        return userDtoRez;
    }
}
